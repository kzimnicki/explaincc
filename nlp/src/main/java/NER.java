import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.List;

public class NER {

    public static void main(String[] args) {

        String serializedClassifier = "edu/stanford/nlp/models/ner/english.muc.7class.distsim.crf.ser.gz";
        AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
        String s1 = "Good afternoon Rajat Raina, how are you today? I go to school at Stanford University, which is located in California.";
        String s2 = "I go to school at Stanford University, which is located in California.";
        List<List<CoreLabel>> classify = classifier.classify(s1);

        for (List<CoreLabel> words : classify) {
            for (CoreLabel word : words) {
                System.out.println(word.get(CoreAnnotations.TextAnnotation.class));
                System.out.println(word.get(CoreAnnotations.AnswerAnnotation.class));
            }
        }
    }
}
