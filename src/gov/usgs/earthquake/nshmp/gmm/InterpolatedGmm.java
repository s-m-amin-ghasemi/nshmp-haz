package gov.usgs.earthquake.nshmp.gmm;

import static com.google.common.base.Preconditions.checkArgument;

import gov.usgs.earthquake.nshmp.data.Interpolator;

/**
 * Class provides internal support for interpolation between spectral periods.
 * Interpolation is linear in period and log in ground motion.
 * 
 * @author Peter Powers
 */
class InterpolatedGmm implements GroundMotionModel {

  private final double tLo;
  private final double tHi;
  private final double tTarget;
  private final GroundMotionModel gmmLo;
  private final GroundMotionModel gmmHi;

  InterpolatedGmm(
      Gmm gmm,
      Imt saLo,
      Imt saHi,
      Imt saTarget) {

    // both arguments must be spectral acceleration
    // IMTs with tLo < tTarget < tHi
    checkArgument(saLo.isSA());
    checkArgument(saHi.isSA());
    checkArgument(saTarget.isSA());
    checkArgument(saLo.ordinal() < saHi.ordinal());

    tLo = saLo.period();
    tHi = saHi.period();
    tTarget = saTarget.period();
    gmmLo = gmm.instance(saLo);
    gmmHi = gmm.instance(saHi);
  }

  @Override
  public ScalarGroundMotion calc(GmmInput in) {
    ScalarGroundMotion sgmLo = gmmLo.calc(in);
    ScalarGroundMotion sgmHi = gmmHi.calc(in);
    double iμ = Interpolator.findY(tLo, sgmLo.mean(), tHi, sgmHi.mean(), tTarget);
    double iσ = Interpolator.findY(tLo, sgmLo.sigma(), tHi, sgmHi.sigma(), tTarget);
    return DefaultScalarGroundMotion.create(iμ, iσ);
  }
}
