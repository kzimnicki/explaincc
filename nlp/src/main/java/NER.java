import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.List;
import java.util.Map;

public class NER {

    public static final AbstractSequenceClassifier CLASSIFIER = CRFClassifier.getClassifierNoExceptions("edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz");
    public static final Gson GSON = new Gson();

    public String process(String text) {
        List<List<CoreLabel>> classify = CLASSIFIER.classify(text);

        Map<String, List<String>> map = Maps.newHashMap();

        for (List<CoreLabel> words : classify) {
            for (CoreLabel word : words) {
                String wordValue = String.valueOf(word.get(CoreAnnotations.TextAnnotation.class));
                String annotation = word.get(CoreAnnotations.AnswerAnnotation.class);
                if(!map.containsKey(annotation)){
                    map.put(annotation, Lists.<String>newArrayList());
                }
                map.get(annotation).add(wordValue);
            }
        }
        return GSON.toJson(map);
    }
}
