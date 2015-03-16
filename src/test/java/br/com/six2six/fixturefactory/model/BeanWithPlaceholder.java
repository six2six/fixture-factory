package br.com.six2six.fixturefactory.model;

public abstract class BeanWithPlaceholder {

    
    protected String attrOne;
    
    protected String attrTwo;
    
    protected String attrThree;

    public String getAttrOne() {
        return attrOne;
    }

    public String getAttrTwo() {
        return attrTwo;
    }

    public String getAttrThree() {
        return attrThree;
    }
    
    public static class Immutable extends BeanWithPlaceholder {
        
        public Immutable(String attrOne, String attrTwo, String attrThree) {
            this.attrOne = attrOne;
            this.attrTwo = attrTwo;
            this.attrThree = attrThree;
        }
    }
    
    public static class Mutable extends BeanWithPlaceholder {
        
         public void setAttrOne(String attrOne) {
             this.attrOne = attrOne;
         }
         
         public void setAttrTwo(String attrTwo) {
             this.attrTwo = attrTwo;
         }
         
         public void setAttrThree(String attrThree) {
             this.attrThree = attrThree;
         }
    }
}
