package org.apache.hadoop.hive.ql.exec.mr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.hive_metastoreConstants;
import org.apache.hadoop.hive.ql.exec.Operator;
import org.apache.hadoop.hive.ql.exec.Utilities;
import org.apache.hadoop.hive.ql.plan.MapWork;
import org.apache.hadoop.hive.ql.plan.MapredWork;
import org.apache.hadoop.hive.ql.plan.PartitionDesc;
import org.apache.hadoop.hive.ql.plan.PlanUtils;
import org.apache.hadoop.hive.ql.plan.TableDesc;
import org.apache.commons.logging.Log;


public class MapredInputFilter implements PathFilter ,Configurable{

	private Configuration conf;
	private MapredWork mrwork;
	private String location;
	private static  Log LOG=LogFactory.getLog("mapredinputfileter");
	private MapWork mapwork;
	private LinkedHashMap<String, PartitionDesc> partdesc;
	@Override
	public boolean accept(Path path) {
		// TODO Auto-generated method stub
		//LOG  = LogFactory.getLog(this.getClass().getName());
	   LOG.error(this.getClass().getName()+"accept:"+"path:"+path);
	   if(Pattern.compile(location).matcher(path.getName()).matches())
	   {
		   LOG.error("mapredInputFilter"+":"+location+"  path:"+path.getName());
		   return true;
	   }
		return false;
		//mrwork.
	}

	@Override
	public void setConf(Configuration conf) {
		// TODO Auto-generated method stub
		this.conf=conf;
		mrwork = Utilities.getMapRedWork(conf);
		//Utilities.get
		LOG  = LogFactory.getLog(this.getClass().getName());
		mapwork=mrwork.getMapWork();
		 LinkedHashMap<String,PartitionDesc> testpart=mapwork.getAliasToPartnInfo();
		// mapwork.getPathToAliases()
		 Set<Entry<String,PartitionDesc>> set1=testpart.entrySet();
    Iterator<Entry<String, PartitionDesc>> ittest=set1.iterator();
    Entry<String,PartitionDesc> tmp1=null;
    PartitionDesc tmppart=null;
    while(ittest.hasNext())
    {
    	tmp1=ittest.next();
    	tmppart=tmp1.getValue();
    	//tmppart.getPartSpec();
    	 LOG.error("TEST:ALIAS__"+tmp1.getKey()+":::  "+tmp1.getValue());
    	 LOG.error("TEST:TEST:");
    }
		
  //  LinkedHashMap<String,String> pathtest=
    	LinkedHashMap<String,ArrayList<String>> testpath=	mapwork.getPathToAliases();
    	Set<Entry<String,ArrayList<String>>> path_set=testpath.entrySet();
    	Iterator<Entry<String,ArrayList<String>>> path_it=path_set.iterator();
    	Entry<String,ArrayList<String>> path_entry=null;
    	while(path_it.hasNext())
    	{
    		path_entry=path_it.next();
    		LOG.error("TEST:PATH:"+path_entry.getKey());
    		if(!path_entry.getValue().isEmpty())
    			LOG.error("value:"+path_entry.getValue().get(0));
    	}
		String alias = mapwork.getAliases().get(0);
		LOG.error("aliases"+alias);
	//	mapwork
		Operator<?> topOp = mapwork.getAliasToWork().get(alias);
	    PartitionDesc partDesc =mapwork.getAliasToPartnInfo().get(alias);
	    //mapwork.getPathToPartitionInfo();
	     LinkedHashMap<String, String > partlist=partDesc.getPartSpec();
       //LOG.error("partdesc"+partDesc.);
	    partDesc.getProperties();
	    TableDesc tabledesc= partDesc.getTableDesc();
	    //Log.error(message);
		//Utilities.get
	   LinkedHashMap<String,String> testspe=partDesc.getPartSpec();
	   Set<Entry<String, String>> partit=testspe.entrySet();
	   Iterator<Entry<String,String>> test_it=partit.iterator();
	   Entry<String,String> partentry=null;
	    while(test_it.hasNext())
	    {
	    	partentry=test_it.next();
	    	LOG.error("parSpec:test:hello:: "+partentry.getKey()+" value::"+partentry.getValue());
	    }
	    Properties tblProps = tabledesc.getProperties();
	  Set<Entry<Object, Object>> set=  tblProps.entrySet();
	  Iterator it=set.iterator();
	  Entry<Object,Object> tmp=null;
	  while(it.hasNext())
	  {
		  tmp=(Entry<Object, Object>) it.next();
		  LOG.error("zzy_test:"+tmp.getKey()+":"+tmp.getValue());
	  }
	        location = tblProps.getProperty(hive_metastoreConstants.META_TABLE_LOCATION);
	       LOG.error(this.getClass().getName()+"   location"+location);
	       
	  // LOG.error(this.getClass().getName()+"TEST:"+tabledesc.getTableName()+tabledesc.getDeserializerClass().getName());
	   String plan = HiveConf.getVar(conf, HiveConf.ConfVars.PLAN);
	  // String test_location= HiveConf.getVar(conf, HiveConf.ConfVars.m)
	   LOG.error("PALN:"+plan);
	   
	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return conf;
	}

}
