package DeLP_GDPR.delp.semantics;

 

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;


public final class CriterionOptionHandler extends OptionHandler<ComparisonCriterion> {

    public CriterionOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super ComparisonCriterion> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters parameters) throws CmdLineException {
        setter.addValue(ComparisonCriterion.Factory.create(parameters.getParameter(0)));
        return 1;
    }

    @Override
    public String getDefaultMetaVariable() {
        return "CRIT";
    }
}