package DeLP_GDPR.delp.semantics;


public class DelpAnswer {

    public enum Type {
        YES ("YES"),
        NO ("NO"),
        UNDECIDED ("UNDECIDED"),
        UNKNOWN ("UNKNOWN");

        private final String text;

        Type(String text) { this.text = text; }

        static Type typeForBoolean(boolean booleanAnswer) {
            if (booleanAnswer)
                return YES;
            else
                return NO; // ambiguous, so default is NO
        }

        static Type typeForDouble(Double doubleAnswer) {
            if (doubleAnswer == 0d)
                return YES;
            else if (doubleAnswer < 0d)
                return NO;
            else // double is positive
                return UNDECIDED;
        }

        @Override
        public String toString() { return text; }

        public boolean getBooleanAnswer() {
            switch (this) {
                case YES: return true;
                default: return false;
            }
        }

        public Double getDoubleAnswer() {
            switch (this) {
                case YES: return 0d;
                case NO: return -1d;
                case UNDECIDED: return 1d;
                case UNKNOWN: return Double.NaN;
                default:
                    throw new IllegalStateException("Cannot generate Double answer from "+this);
            }
        }
    }

   
}