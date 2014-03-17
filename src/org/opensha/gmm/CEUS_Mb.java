package org.opensha.gmm;

import static org.opensha.gmm.MagConverter.MB_TO_MW_JOHNSTON;
import static org.opensha.gmm.MagConverter.MB_TO_MW_ATKIN_BOORE;

/**
 * Wrapper class for CEUS GMM flavors that do magnitude conversions from
 * m<sub>b</sub> to M<sub>w</sub>. For use with 2008 CEUS grid sources only.
 * 
 * @author Peter Powers
 */
class CEUS_Mb {

	private static final String J_NAME =  ": mb(J)";
	private static final String AB_NAME =  ": mb(AB)";

	// @formatter:off
	
	static final class AtkinsonBoore_2006_140bar_J extends AtkinsonBoore_2006_140bar {
		public static final String NAME = AtkinsonBoore_2006_140bar.NAME + J_NAME;
		AtkinsonBoore_2006_140bar_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class AtkinsonBoore_2006_140bar_AB extends AtkinsonBoore_2006_140bar {
		public static final String NAME = AtkinsonBoore_2006_140bar.NAME + AB_NAME;
		AtkinsonBoore_2006_140bar_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}

	
	
	static final class AtkinsonBoore_2006_200bar_J extends AtkinsonBoore_2006_200bar {
		public static final String NAME = AtkinsonBoore_2006_200bar.NAME + J_NAME;
		AtkinsonBoore_2006_200bar_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class AtkinsonBoore_2006_200bar_AB extends AtkinsonBoore_2006_200bar {
		public static final String NAME = AtkinsonBoore_2006_200bar.NAME + AB_NAME;
		AtkinsonBoore_2006_200bar_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}

	
	
	static final class Campbell_2003_J extends Campbell_2003 {
		public static final String NAME = Campbell_2003.NAME + J_NAME;
		Campbell_2003_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class Campbell_2003_AB extends Campbell_2003 {
		public static final String NAME = Campbell_2003.NAME + AB_NAME;
		Campbell_2003_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}

	
	
	static final class FrankelEtAl_1996_J extends FrankelEtAl_1996 {
		public static final String NAME = FrankelEtAl_1996.NAME + J_NAME;
		FrankelEtAl_1996_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class FrankelEtAl_1996_AB extends FrankelEtAl_1996 {
		public static final String NAME = FrankelEtAl_1996.NAME + AB_NAME;
		FrankelEtAl_1996_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}

	
	
	static final class SilvaEtAl_2002_J extends SilvaEtAl_2002 {
		public static final String NAME = SilvaEtAl_2002.NAME + J_NAME;
		SilvaEtAl_2002_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class SilvaEtAl_2002_AB extends SilvaEtAl_2002 {
		public static final String NAME = SilvaEtAl_2002.NAME + AB_NAME;
		SilvaEtAl_2002_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}
	
	
	
	static final class TavakoliPezeshk_2005_J extends TavakoliPezeshk_2005 {
		public static final String NAME = TavakoliPezeshk_2005.NAME + J_NAME;
		TavakoliPezeshk_2005_J(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_JOHNSTON; }
	}
	
	static final class TavakoliPezeshk_2005_AB extends TavakoliPezeshk_2005 {
		public static final String NAME = TavakoliPezeshk_2005.NAME + AB_NAME;
		TavakoliPezeshk_2005_AB(IMT imt) { super(imt); }
		@Override public MagConverter converter() { return MB_TO_MW_ATKIN_BOORE; }
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO do nothing

	}

}