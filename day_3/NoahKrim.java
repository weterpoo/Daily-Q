//time: 
    //best: O(n)  
    //worst: O(n*log(n))
//size: O(n)

import java.io.*;
import java.util.*;
import java.math.*;

public class NoahKrim {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashMap<Integer, Astronaut> map = new HashMap<>();
        int n = in.nextInt();
        int p = in.nextInt();
        for(int i_p=0; i_p<p; i_p++)    {
            int x = in.nextInt();
            int y = in.nextInt();
            Astronaut a_x, a_y;
            if((a_x = map.get(x)) != null) {
                if((a_y = map.get(y)) != null) {
                    Country c_x = a_x.getCountry();
                    Country c_y = a_y.getCountry();
                    if(c_x.getCount() > c_y.getCount())
                        c_x.absorbCountry(c_y);
                    else
                        c_y.absorbCountry(c_x);
                }
                else    {
                    a_y = new Astronaut(y, a_x.getCountry());
                    map.put(y, a_y);
                }
            }
            else if((a_y = map.get(y)) != null)    {
                a_x = new Astronaut(x, a_y.getCountry());
                map.put(x, a_x);
            }
            else    {
                Country c = new Country();
                a_x = new Astronaut(x, c);
                map.put(x, a_x);
                a_y = new Astronaut(y, c);
                map.put(y, a_y);
            }
        }
        
        HashSet<Country> countries = new HashSet<>();
        for(int i_n=0; i_n<n; i_n++)  {
            Astronaut a = map.get(i_n);
            if(a == null)   {
                Country c = new Country();
                a = new Astronaut(i_n, c);
            }
            countries.add(a.getCountry());
        }
        int total_count = 0;
        BigInteger combos = new BigInteger("0");
        for(Iterator<Country> it=countries.iterator(); it.hasNext();)   {
            Country c = it.next();
            combos = combos.add(new BigInteger((total_count * c.getCount())+""));
            total_count += c.getCount();
        }
        System.out.println(combos);
    }
    
    static class Astronaut  {
        private int num;
        private Country country;
        
        public Astronaut(int num)   {
            this.num = num;
            this.country = null;
        }
        public Astronaut(int num, Country country)   {
            this.num = num;
            this.setCountry(country);
        }
        
        public int getNum()         {   return num;     }
        public Country getCountry() {   return country; }
        public int hashCode()       {   return num;     }
        
        public boolean setCountry(Country c) {
            country = c;
            return c.add(this);
        }
    }
    
    static class Country   {
        private int count;
        private int max_num;
        private HashSet<Astronaut> set;
        
        public Country()    {
            this.count = 0;
            this.max_num = 0;
            this.set = new HashSet<>();
        }
        
        public int getCount()               {   return count;   }
        public HashSet<Astronaut> getSet()  {   return set;     }
        public int hashCode()               {   return max_num; }
        
        public boolean add(Astronaut a)    {
            if(set.add(a))  {
                count++;
                if(a.getNum() > max_num)
                    max_num = a.getNum();
                return true;
            }
            return false;
        }
        public void absorbCountry(Country o)    {
            if(this == o)
                return;
            HashSet<Astronaut> otherSet = o.getSet();
            for(Iterator<Astronaut> it=otherSet.iterator(); it.hasNext();)  {
                Astronaut a = it.next();
                a.setCountry(this);
            }
        }
    }
}