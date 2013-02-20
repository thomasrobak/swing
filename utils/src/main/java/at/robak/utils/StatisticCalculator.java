/*
 * StatisticCalculator.java
 *
 * Created on 4. Juli 2006, 10:15
 *
 */
/**
 *
 * @author Thomas Robak
 */

package at.robak.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class StatisticCalculator {
    public static int SCALE=2;
    public double[] origdata=null;
    public double[] data=null;
    
    public double max=0;
    public double mean=0;
    public double median=0;
    public double min=0;
    public double sum=0;
    public double count=0;
    public double var=0;
    public double sd=0;
    public double skew=0;
    public double[] quartile=new double[0];
    public double iqr=0;
    
    public List medianvalue=new ArrayList(0);
    public List medianindex=new LinkedList();
    
    public StatisticCalculator(double[] data) {
        super();
        this.origdata=data;
        this.data=new double[data.length];
        for(int i=0;i<this.origdata.length;++i){
            this.data[i]=this.origdata[i];
            this.sum+=this.data[i];
        }
        Arrays.sort(this.data);
        this.count=this.data.length;
        this.min=this.data[0];
        this.max=this.data[data.length-1];
        this.mean=this.sum/this.data.length;
        if(this.data.length%2==0){
            this.medianvalue=new ArrayList(2);
            this.medianvalue.add(0,new Double(this.data[this.data.length/2-1]));
            this.medianvalue.add(1,new Double(this.data[this.data.length/2]));
/*            this.median=(this.medianvalue.get(0).doubleValue()+
                            this.medianvalue.get(1).doubleValue())/2;
 */
        } else {
            this.medianvalue=new ArrayList(1);
//            this.median=this.data[this.data.length/2];
            this.medianvalue.add(0,new Double(this.median));
        }
        for(int i=0;i<this.data.length;++i){
            this.sd+=Math.pow(this.data[i]-this.mean,2);
            this.skew+=Math.pow(this.data[i]-this.mean,3);
            if(this.medianvalue.contains(new Double(this.origdata[i]))){
                this.medianindex.add(new Integer(i));
            }    
        }
        this.sd=Math.sqrt(this.sd/(this.count-1));
        this.var=Math.pow(this.sd,2);
        this.skew/=(this.count*Math.pow(this.sd,3));
        
        /*
         *  Quartile
         */
        
        double[] probs=new double[]{0.0,0.25,0.5,0.75,1.0};
        double[] index=new double[probs.length];
        double[] h=new double[probs.length];
        int[] lo=new int[probs.length];
        int[] hi=new int[probs.length];
        this.quartile=new double[probs.length];
        
        for(int i=0;i<probs.length;++i){
            index[i]=(this.count-1)*probs[i];
            lo[i]=(int)Math.floor(index[i]);
            hi[i]=(int)Math.ceil(index[i]);
            h[i]=index[i]-(double)lo[i];
        }
        
        for(int i=0;i<probs.length;++i){
            this.quartile[i]=lo[i]==hi[i]?this.data[lo[i]]:
                (1-h[i])*this.data[lo[i]]+(h[i])*this.data[hi[i]];
        }
        this.median=this.quartile[2];
        this.iqr=this.quartile[3]-this.quartile[1];
/*        
        System.out.println("Index: "+Arrays.toString(index));
        System.out.println("Lo: "+Arrays.toString(lo));
        System.out.println("Hi: "+Arrays.toString(hi));
*/        
    }
    
    public String countToString(){
        return "Count: "+this.count;
    }
    public String dataToString(){
        return Arrays.toString(this.data);
    }
    public String minToString(){
        return (new BigDecimal(this.min)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String maxToString(){
        return (new BigDecimal(this.max)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String meanToString(){
        return (new BigDecimal(this.mean)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String medianToString(){
        return (new BigDecimal(this.median)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String sumToString(){
        return (new BigDecimal(this.sum)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String varToString(){
        return (new BigDecimal(this.var)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String sdToString(){
        return (new BigDecimal(this.sd)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String skewToString(){
        return (new BigDecimal(this.skew)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String q25ToString(){
        return (new BigDecimal(this.quartile[1])).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String q75ToString(){
        return (new BigDecimal(this.quartile[3])).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    public String iqrToString(){
        return (new BigDecimal(this.iqr)).setScale(SCALE,BigDecimal.ROUND_HALF_UP).toString();
    }
    
    public String toString(){
//        return  this.dataToString()+"\n"+
//        return  this.countToString()+"\n"+
        return  this.minToString()+"\n"+
                this.q25ToString()+"\n"+
                this.medianToString()+"\n"+
                this.q75ToString()+"\n"+
                this.maxToString()+"\n"+
                this.meanToString()+"\n"+
                this.sumToString()+"\n"+
                this.varToString()+"\n"+
                this.sdToString()+"\n"+
                this.skewToString()+"\n"+
                this.iqrToString();
    }
    
    public static void main(String[] args){
        double[] data=new double[8];
        for(int i=0;i<data.length;++i){
//            data[i]=(Math.pow(3,i));
            data[i]=Math.random()*1000;
        }
        
        StatisticCalculator sc=new StatisticCalculator(data);
        System.out.println(Arrays.toString(sc.quartile));
        System.out.println(Arrays.toString(data));
        System.out.println(sc.toString());
    }
}
