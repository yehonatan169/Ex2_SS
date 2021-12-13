package MainClasses;

//this class is built to serve as keys for edges hashmap.
public class HashIndex {
    private int src;
    private int dest;

    public HashIndex(int src, int dest){
        this.src = src;
        this.dest = dest;
    }

    public int getSrc(){
        return this.src;
    }

    public int getDest(){
        return this.dest;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        else if(!(o instanceof HashIndex)){
            return false;
        }
        HashIndex compared = (HashIndex) o;
        return (this.dest == compared.dest && this.src == compared.src);
    }

    @Override
    public int hashCode(){
        return this.src + this.getDest();
    }


}
