Storm2-WordCountWithGroupingsAndAck
===================================

//2 instances of spout are created here. For expected output run the examples from the groupings.
builder.setSpout(SENTENCE_SPOUT_ID, spout, 2);

----------------------------------
Topology is created and deleted after run. Change below code in prod.

 cluster.submitTopology(TOPOLOGY_NAME, config, builder.createTopology());
        waitForSeconds(10);
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();
---------------------------------
checkins.txt and log4j are present in resources folder of maven project. Once the jaar is created, it is in class path.

---------------------------------
------------------------------------------- Starting servers in storm-----------------------------------

Note: Use separate terminals to start Nimbus, Supervisor and UI nodes.

1.	Make sure to start zookeeper.

2.	Open New Terminal and execute $ sudo bin/storm nimbus

3.	Open New Terminal and execute $ sudo bin/storm supervisor

4.	Open New Terminal and execute $ sudo bin/storm ui
----------------------------------------- Create jar file of the Topology by running maven ------

Run Goal as mvn install. This will generate jar file(Storm-HelloWorld-0.0.1-SNAPSHOT.) in target folder. 

---------------------------------------- Submitting Storm Jobs to the cluster -----------------------
bin/storm jar /home/edureka/StormExecution/Storm-HelloWorld-0.0.1-SNAPSHOT.jar com.edureka.storm.helloworld.FirstTopology

---------------------------------------- Open STORM UI login -----------------------------------------

http://localhost:8772

see the topology, spouts and bolts from the UI.







---------------------------------My Notes on Storm ------------------------------------------

---------->Installation<--------------------------

Go through the installation of storm, zoo keeper, node js , redis, zeromq etc.
Install Storm UI

Node.js for UI
STORM for processing
REDIS for database ..Its ket value store.
Linux comes built in with Phython.

Isues in Intallation:

1. Install GIT : sudo yam install git

2. make sure you create zoo.cfg by using below commands
$ sudo mkdir /var/zoo
$ sudo gedit zookeeper-3.4.6/conf/zoo.cfg

3. STORM.YAML file contains port for nimbus,sueprvisor and UI

------------------STORM-----------------------------------------------

Storm is Distributed and Fault Tolerant system for processing Streams of data.

------------------STORM NODES ----------------------------------------------

Strom cluster has 3 nodes Nimbus, zoo keeper and Supervisor Nodes.

Nimbus node. --> It is a master node in storm cluster. It submits workers to supervisor nodes.

********  STORM job is submitted by Nimbus node and job keeps on running untill it is killed. not like map reduce job which finishes after some period of time.

Nimbus is a daemon. Nimbus is run on one of the machines.

Zoo Keeper is used for co-ordination between nimbus and supervisor nodes. It mnoitors the storm jobs.

Supervisor node starts and stops workers with signals from Nimbus. It co-ordinates with Master i.e. Nimbus through ZK.

--------------------STORM COMPONENT-------------------------------------------

TOPOLOGY --> combination of SPOUTS+BOLTS but it should have only one SPOUT but many BOLTS.

SPOUT --> Consumer of Input data from the source of streams (Source can be Kafa,JMS, logs, RDBMS, Steam, file , directory). Receive stream from Input source.
Basic Filtering or aggregation is done in SPOUT

BOLT --> Bolt receives streams from SPOUT. Aggregation or filtering is done in BOLT and final data is sent to either BOLT or HDFS.
Transformation of data is done on bolt.

TUPLE -> Order collection of elemements

STREAM -> Unbound sequence of tuples.

Map--> Spout
Reduce --> Bolt
Driver --> Topology

--------------------Horton Works tutorial -----------------------------------------
Nimbus node. The master node (Similar to JobTracker)
Supervisor nodes. Starts/stops workers & communicates with Nimbus through Zookeeper
ZooKeeper nodes. Coordinates the Storm cluster
Here are a few terminologies and concepts you should get familiar with before we go hands-on:

Tuples. An ordered list of elements. For example, a “4-tuple” might be (7, 1, 3, 7)
Streams. An unbounded sequence of tuples.
Spouts. Sources of streams in a computation (e.g. a Twitter API)
Bolts. Process input streams and produce output streams. They can:
Run functions;
Filter, aggregate, or join data;
Talk to databases.
Topologies. The overall calculation, represented visually as a network of spouts and bolts

--------------------STORM USE CASES-------------------------------------------
Click Stream Analysis -- We need to get data regularly Sears, Amazon . Click stream is for continously reading like buying etc.
Web Log analysis -- you can do batch processing daily once etc using hadoop . But you can do it in STORM but not required.

--------------------Assignment1 ----------------------------------------------

Hadoop is not good for Real Time Analytics but STORM is good for real time Analytics. Hadoop is good for batch processing.

Supervisor node in Hadoop is equal to Node Manager in hadoop

Nimbus node is master node and equal to Job tracker in Hadoop.

Nimbus is not a single point of failure(Partial Failure). Existing topologies will run without any issues but new topologies cannot be submitted.

Storm topology can be used for Linear Regression Analysis not others in below options.
URL Indexing
Fraud detection
Click stream processing
Linear Regression Analysis.

Bolts cannot affect structure of data streams FALSE.

STORM topology is submitted by Nimbus

Stom Bolt can be source of stream. True but spout cannot be. Output of Bolt can be send to Spout again.(I am not sure but may be).

Storm is polygolt in nature

STORM topology is not daemon but it is sequence of programs.

STORM clusters can be scaled horizontally and vertically

STORM INPUT can be file, directory, twitter, Mesage queue, krestel queue, kafka etc.

Pig Latin is data flow language not declarative or data warehouse package.

Data from Mainframe is ingested by FTP tools not scoop or flume.

Web Crawlers are example of real time processing.

Zookeeper for maintaining cluster state.

No scalability for nimbus. It is only one for the entire storm cluster .

######################################SESSION2######################################################################
######################################SESSION2######################################################################
######################################SESSION2######################################################################
######################################SESSION2######################################################################
######################################SESSION2######################################################################

------------------------------ STORM Running modes --------------------------------------------------------------------

Local Mode: Topologies of STORM run in same jvm in same machine. Debugging is allowed. Need to download extra jars for debugging(Storm Development Dependenices)

Remote or distributed mode: Run in different JVM. Debugging not allowed.
------------------------------ STORM COMMANDS  Starting and Stoping STORM cluster-----------------------------------------

Go to Bin directory of STORM Installation and execute below commands

****Note: Make sure Zoop keeper is already running***

storm nimbus
storm UI
strom supervisor

check the UI port in storm.yaml file 

--------------------------- STORM JOB SUBMISSION --------------------------------------
Go to Bin directory of STORM Installation and execute below commands

****Note: Make sure Zoop keeper is already running***

storm jar topologyjar topologyclass arguments

--------------------------- STORM JOB SUBMISSION Differnt States -----------------------

STORM kill toplologyname    (Undeploys or removes topology. It first deactivates topology before removing)

STORM activate topologyname (deactivate topology i.e. Stops streaming )

STORM deactivate topologyname (avtivate topology i.e. resumes streaming )

STORM recombine topologyname   (useful when new node is added to cluster. to submit or balance topology on newly added node)

----------------------------------------------------------------------------------------
Parallelization

Creating Multiple JVM instances -->(cluster level)--> conf.setNumofWorkers(20) --> Increasing number of JVM's

Creating Multiple Executers(ThreadLevel) within single JVM  --> builder.setSpout("firstspout",new FirstSPout(),2) -- This is on single JVM. Thread instances are increased.

Asmath-->Data Parallelization is later done by grouping. load balancing , dividing date etc.
https://storm.apache.org/documentation/Concepts.html

***************************** See figures of Grouping from Edureka --you can easily get idea of mappings *********************

1. Shuffle Grouping: Load Balancing. Multiple instances of same bolt or multiple different bolts receive equal number of tuples from the source(spout). odd number of bolts gets equal and rest one get extra one.
builder.setBolt("Bolt ALias", new Bolt()).ShuffleGrouping("Spout Alias")

2. Fields Grouping : All the elements of single key(Tuple) come together to one istance of boult.(similar to partition in Map Reduce)
Mainly used in joining datasets.
builder.setBolt("Bolt ALias", new Bolt()).FieldGrouping("Bolt2",new filed("word")mnew filed("word2"))

input.getStreamId --componentID
The stream is partitioned by the fields specified in the grouping. 
For example, if the stream is grouped by the "user-id" field, tuples with the same "user-id" will 
always go to the same task, but tuples with different "user-id"'s may go to different tasks

3. All Grouping: Everything from the bolt is sent to everything (all the bolts) (Refresh cache use case).Use this grouping with care

4. Direct Grouping: Source will decide destination. collector.emitDirect instead of collector.emit.
prepare 
{
numberofTasks=context.getComponentTasks
}

5. Global Grouping: Tuples generated by all instances of source go to single target instance.
Many-One Relation

6. None Grouping -Shuffle grouping.

7. Custom Grouping: user will define which bolts recive each bolt.

************************* STORM running modes************
LocalCluster  --> submits storm job to local cluster

StormSubmitter  --> submits storm job to cluster.

 if(args!=null && args.length > 0) {
      // submit to cluster
      config.setNumWorkers(3);
      StormSubmitter.submitTopology(args[0], config, builder.createTopology());
    } else {
      // local cluster
      config.setMaxTaskParallelism(3);
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("kafka", config, builder.createTopology());
      Thread.sleep(50000);
      cluster.shutdown();
    }

####### Questions ########################

Can I run storm without istallating hadoop in the machine? if so, why zookeeper is required since it is part of hadoop ecosystem?

If Hadoop installation is not required for STORM?  where and how it is fit into hadoop? some slides say we can run STORM on YARN but it is still not present.

STORM supports parallelisation but why the user has control on parallelisation? IN hadoop, frameworks take care of no of splits but in storm we explictly declare in conf.setNumofWorkers(20)
or builder.setSpout("firstspout",new FirstSPout(),2)? if we set parallelisation on spouts (Ex:2 Parallelization),
then 2 threads are run and both the threads process different data of stream like mapper in mapreduce?

I have one spout and 3 boltts (bolt1,bolt2,bolt3).. How the data is sent to bolt and what is the sequence?






######################################SESSION3######################################################################
######################################SESSION3######################################################################
######################################SESSION3######################################################################
######################################SESSION3######################################################################
######################################SESSION3######################################################################

SPOUTS --> spouts connect to datasource not the other way round. It receives tuples from source.

Mian Class -- Extens BaseRichSpout or implements IRichSpout

Life cycle methods

activate: called when spout is activated (indirectly when topology is activated)

deactivate: called when spout is deactivated (Indirectly when topology is deactivated)

close: called when closing the streams .. (Indirectly when topology is killed)

awk(object tuppleId) : called when tuple with TuppleId is processed successfully

fail(object tuppleId) : called when tuple with TuppleId is failed while processing. Ex: Losing transaction in one bank

open(): this method is called when spout is making connection with datasource or doing initilaizations on context,map or collector.

nextTuple(): storm keeps on calling next tuple on the spout. storm --next tuple,next tuple,next tuple,next tuple,next tuple,next tuple, ...
untill kill is called.
next tuple will fetch the data and emit using collector.emit (similar to context.write)

declareOutPutFields Method.

Very IMP*****All methods are called once or when user called calls the method but nextTuple is called as long as the kill is executed.

Implement Open and nextTuple ..others are not required.

fail and awk are reliable spouts methods so same methods should be implemented in bolt aslo if we are using it.

Acknowlegement in SPOUT: TupleId is passed in awk or fail methods to notify. 

------------------------------------------------------------------------------------------

BOLTS

Toplology need not have bolts like reducers in map reduce.

Below is sequence in which methods are executed.

prepare() called only once in life time.  --> can be empty --- getting database connections, properties. like init()method called only once.
execute() --> business logic is written  --> 
declareOutputFields(OutputFieldsDeclarer declarer) --> declare output schema of bolt.--- can be empty.. input.get[0] sequentially or input.getByFileld("word")
cleanup() --> shutdown method or kill the topology.


Question? declareOutputFields(OutputFieldsDeclarer declarer)  what is declarer.declare(new Fields("myHello"));


######################################SESSION4######################################################################
######################################SESSION4######################################################################
######################################SESSION4######################################################################
######################################SESSION4######################################################################
######################################SESSION4######################################################################

Trident Toplology: Trident Toplogies emit tuples in form of batches. It also support transactions. handles failures efficiently (all 3 points are imp)
High level abstraction running on apache storm.
It doesnt have bolts.
Reduces the number of lines of code.

Trident Spouts -----> 2 components.  
Batch Cordinatiorn(Responsible for Batch management and Metadata.contains metadata for the data residing in batch) and Emitter Function(Responsible for Emiting tuples).

TridentSpout class implements ITridentSpout (4 methods)
------------------------------------------------------
BatchCoordinator getCoordinator()
Emitter getEmitter()
Map getComponentConfiguration()
Fields getOutputFields()

BatchCoordinator Interface  --> Responsible for Batch management and Metadata. Stores metadata in zookeeper. 
Create Metadata for transactionid which has never created before.
--------------------------
InitializeTransaction  --> Creates metadata for this transaction id which has never emitted before.
void success()
boolean isReady() --Not required
void close() --Not required

Emitter Interface
-----------------------
emitBatch  -> Emit tuples in batch for a trasaction
success --> commit the transaction.
close  --> Release resources before transaction ends.

*****************************************************
Trident Filters: Filter Interface

boolean isKeep method --> Implement logic to keep or remove the tuple from the batch using the filter.

*****************************************************

Trident Functions: Trident functions will help in adding new fields to the tuples. Existing fields cannot be modified or removed. 
Similar to Bolts, Trident functions has execute method. 
Output emitted from execute method is extra field to the tuple.***

*****************************************************
Trigent Aggregators : with Trident Aggregators, tupple fields can be modified.
3 types of Aggregators -- combiner aggregator, reducer aggregator, aggregator

Combiner Aggregator: combies set of tuples into single filed
init,combine and zero are three methods of CombinerAggregator Interface.
init called only once. combine method calls all tuples in a batch and combines set of tuples into single filed. zero method is called when batch is empty.
combines fields of multople ytuples to single filed like count.

ReducerAggregator: this will return tuple from sets of tuples.(reducing tuples to single tuple)
Init method
Reduce method.

Aggregator : will return more than one tuples.
init
aggregate
complete method

**********************************************************

Transactional States --> Repeat, Opaque and Non-transactional states

Repeat: 
Tuple with 3 fields(f1,f2,f3 --key from external source is 4)
Tuple with 3 fields(f1,f2,f3 --key from external source is 10)
Tuple with 3 fields(f1,f2,f3 --key from external source is 8)

Opaque: 

********************************************************


----------------------------------------------------------------------------------------------------------
