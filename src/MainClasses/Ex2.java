package MainClasses;

import GUI.frameGUI;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;

import java.io.IOException;

/**
 * This class is the main class for MainClasses.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGraph(String json_file) {
        DirectedWeightedGraphAlgorithms algo = getGrapgAlgo(json_file);
        algo.load(json_file);
        return algo.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        return new DWGA();
    }

    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraph alg = getGraph(json_file);
        frameGUI gui = new frameGUI(alg);
    }

    public static void main(String[] args) throws IOException {
        //G1 is displayed as the default graph
        //runGUI("data/G1.json");
        if(args.length>0){
            System.out.println(args[0]);
            runGUI(args[0]);
        }
        else {
            String s="no json file has been loaded, pleas upload a graph that exist in the project or creat your one graph in the same format as a json file and then upload him";
            throw new IOException(s);
        }
    }
}