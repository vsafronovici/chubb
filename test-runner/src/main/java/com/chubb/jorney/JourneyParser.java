package com.chubb.jorney;

import com.chubb.exception.ActionException;
import com.chubb.exception.ChubbException;
import com.chubb.exception.ChubbExceptionReason;
import com.chubb.exception.SevereException;
import com.chubb.jorney.actions.Action;
import com.chubb.util.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Class that parses a journey file and invokes it's actions.
 *
 * Created by ichistruga on 10/18/2016.
 */
@Service
public class JourneyParser {

    @Autowired
    private ApplicationContext applicationContext;

    private static final String VERB_FIELD = "verb";
    private static final String NAME_FIELD = "paramName";
    private static final String VALUE_FIELD = "paramValue";

    /**
     * Parses a journey file and invokes it's actions.
     *
     * @param journeyFile journey file to be parsed
     */
    public void parse(File journeyFile) {


        final List<Map<String, String>> journeyFileCsvData;
        try {
            journeyFileCsvData = CsvReader.readCSV(journeyFile);
        } catch (IOException e) {
            throw new SevereException(String.format("Invalid CSV format for %s", journeyFile.getAbsolutePath()));
        }

        for (Map<String, String> step : journeyFileCsvData) {
            Verb verb;
            try {
                verb = Verb.valueOf(step.get(JourneyParser.VERB_FIELD));
            } catch (IllegalArgumentException e) {
                throw new ChubbException(ChubbExceptionReason.SEVERE, String.format("Invalid verb '%s' provided", step.get(JourneyParser.VERB_FIELD)));
            }
            String paramName = step.get(JourneyParser.NAME_FIELD);
            String paramValue = step.get(JourneyParser.VALUE_FIELD);
            Action action = (Action) applicationContext.getBean(verb.name() + Action.ACTION);
            try {
                action.execute(paramName, paramValue);
            } catch (ChubbException e) {
                throw new ActionException(verb, e);
            }
        }

    }


}
