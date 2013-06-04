import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class Tag implements Comparable{
    protected String name;
    protected ArrayList<String> values;
    protected WebElement element;
    protected Point p;
    protected int d, curValue;

    public Tag(WebElement e) {
        element = e;
        p = e.getLocation();
        d = Integer.MAX_VALUE;
        name = "";
        curValue = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateDistAndName(String name, int x, int y) {
        int newD = (p.x - x) * (p.x - x) + (p.y - y) * (p.y - y);
        if(newD < d){
            d = newD;
            this.name = name;
        }
    }

    public abstract String getValue();

    public abstract void setValue(String value);

    public abstract String toString();

    @Override
    public int compareTo(Object o) {
        Tag tag = (Tag)o;
        return p.y == tag.p.y ? Integer.compare(p.x, tag.p.x) : Integer.compare(p.y, tag.p.y);
    }

}
