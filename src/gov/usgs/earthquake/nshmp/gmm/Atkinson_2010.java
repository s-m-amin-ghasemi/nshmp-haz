package gov.usgs.earthquake.nshmp.gmm;

import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.DIP;
import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.MW;
import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.RAKE;
import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.RJB;
import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.VS30;
import static gov.usgs.earthquake.nshmp.gmm.GmmInput.Field.WIDTH;
import static gov.usgs.earthquake.nshmp.gmm.GmmUtils.BASE_10_TO_E;
import static java.lang.Math.log10;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Map;

import com.google.common.annotations.Beta;
import com.google.common.collect.Range;

import gov.usgs.earthquake.nshmp.eq.Earthquakes;
import gov.usgs.earthquake.nshmp.eq.fault.Faults;
import gov.usgs.earthquake.nshmp.gmm.GmmInput.Constraints;

/**
 * Implementation of the ground motion model by Atkinson (2010) for shallow and
 * deep earthquake in Hawaii. This model applies a Hawaii specific correction
 * factor to {@link BooreAtkinson_2008}.
 *
 * <p><b>Note:</b>August 15, 2020 email from Gail Atkinson confirms that shallow
 * event model is about 0.05 log units lower than what is shown in Figure 7 of
 * the reference publication. At short periods, this model and
 * {@link BooreAtkinson_2008} should be the same.
 *
 * <p><b>Note:</b> Direct instantiation of {@code GroundMotionModel}s is
 * prohibited. Use {@link Gmm#instance(Imt)} to retrieve an instance for a
 * desired {@link Imt}.
 *
 * <p><b>Reference:</b> Atkinson, G.M., 2010, Ground-motion prediction equations
 * for Hawaii from a referenced empirical approach: Bulletin of the
 * Seismological Society of America, v. 100, n. 2, p. 751-761.
 *
 * <p><b>doi:</b><a href="http://dx.doi.org/10.1785/0120090098" target="_top">
 * 10.1785/0120090098</a>
 *
 * <p><b>Component:</b> geometric mean of two horizontal components
 *
 * @author Peter Powers
 * @see Gmm#ATKINSON_10
 */
@Beta
public class Atkinson_2010 implements GroundMotionModel {

  static final String NAME = "Atkinson (2010) : Hawaii";

  /* contains only supported periods; drops PGV, PGD relative to BA08 */
  static final CoefficientContainer COEFFS = new CoefficientContainer("Atkinson10.csv");

  static final Constraints CONSTRAINTS = Constraints.builder()
      .set(MW, Range.closed(5.0, 8.0))
      .set(RJB, Range.closed(0.0, 200.0))
      .set(DIP, Faults.DIP_RANGE)
      .set(WIDTH, Earthquakes.CRUSTAL_WIDTH_RANGE)
      .set(RAKE, Faults.RAKE_RANGE)
      .set(VS30, Range.closedOpen(180.0, 1300.0))
      .build();

  /* Gail recommends use of frequency-independent sigma */
  private static final double σ = 0.26 * BASE_10_TO_E;

  private static final class Coefficients {
    /* Scale factor only used by caldera modification. */
    final double scale;

    Coefficients(Imt imt, CoefficientContainer cc) {
      Map<String, Double> coeffs = cc.get(imt);
      scale = coeffs.get("scale");
    }
  }

  final Coefficients coeffs;
  private final BooreAtkinson_2008 delegate;
  private final double log10freq;

  Atkinson_2010(final Imt imt) {
    coeffs = new Coefficients(imt, COEFFS);
    delegate = (BooreAtkinson_2008) Gmm.BA_08.instance(imt);
    log10freq = log10(imt.frequency());
  }

  @Override
  public ScalarGroundMotion calc(final GmmInput in) {
    /* Force delegate to return ground motion for 'unknown' focal mechanism. */
    GmmInput inNoStrike = GmmInput.builder().fromCopy(in).rake(Double.NaN).build();
    double μ = delegate.calc(inNoStrike).mean() + hiTerm(in.rJB, in.zTop);
    return DefaultScalarGroundMotion.create(μ, σ);
  }

  private double hiTerm(double rJB, double zTop) {
    double x1 = min(-0.18 + 0.17 * log10freq, 0.0);
    double x0 = 0.2;
    if (zTop < 20) {
      x0 = max(0.217 - 0.321 * log10freq, 0.0);
    } else if (zTop > 35) {
      x0 = min(0.263 + 0.0924 * log10freq, 0.35);
    }
    double logA = x0 + x1 * log10(max(1.0, rJB));
    return logA * BASE_10_TO_E;
  }

  /*
   * Custom version of Atkinson that applies period-dependent scale factors to
   * bring spetrum in line with observations for caldera collapse events.
   * Caldera events are usually ~M5.2.
   */
  static final class Caldera extends Atkinson_2010 {
    static final String NAME = Atkinson_2010.NAME + " : Caldera";

    Caldera(Imt imt) {
      super(imt);

    }

    @Override
    public final ScalarGroundMotion calc(final GmmInput in) {
      ScalarGroundMotion sgm = super.calc(in);
      return new DefaultScalarGroundMotion(
          sgm.mean() + coeffs.scale * BASE_10_TO_E,
          sgm.sigma());
    }
  }

}
