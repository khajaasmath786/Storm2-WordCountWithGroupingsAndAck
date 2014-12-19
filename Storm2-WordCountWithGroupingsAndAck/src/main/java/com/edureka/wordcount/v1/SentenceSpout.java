package com.edureka.wordcount.v1;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.edureka.utils.Utils;

public class SentenceSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private List<String> LinesFromFile;
    private int nextEmitIndex;
    
   /* private String[] sentences = {
        "I love big data",
        "I also love real time analytics",
        "i enrolled with edureka",
        "and enrolled for storm course",
        "now I love edureka"
    };
    private int index = 0;*/

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }

    public void open(Map config, TopologyContext context, 
            SpoutOutputCollector collector) {
        this.collector = collector;
        this.nextEmitIndex = 0;

        try {
        	// txt file is in resources folder of maven project.
        	LinesFromFile = IOUtils.readLines(ClassLoader.getSystemResourceAsStream("checkins.txt"),
                                       Charset.defaultCharset().name());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
    }

    public void nextTuple() {
    	while(LinesFromFile.size()>nextEmitIndex){
    	String line = LinesFromFile.get(nextEmitIndex);    	
        this.collector.emit(new Values(line));
        nextEmitIndex++;
        Utils.waitForMillis(1);
    	}
    }
}
