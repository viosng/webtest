import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public class InputTag extends Tag{

    public InputTag(WebElement e) {
        super(e);
        values = new ArrayList<String>();
        values.add("текст");
        String name = element.getAttribute("placeholder");
        if (name != null && !name.equals("")) {
            this.name = name;
            d = 0;
        } else {
            name = element.getAttribute("value");
            if (name != null && !name.equals("")) {
                this.name = name;
                d = 0;
            }
        }
    }

    @Override
    public String getValue() {
        return values.get(0);
    }

    @Override
    public void setValue(String value) {
        values.set(0, value);
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        Dimension d = element.getSize();
        if(y > p.y + d.getHeight() || x > p.x + d.getWidth()) return;
        super.updateDistAndName(name, x, y);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("В текстовое поле");
        if(!name.equals("")) buffer.append(" \"" + name + "\"");
        buffer.append(" написать \"");
        buffer.append(values.get(0));
        buffer.append("\".");
        return buffer.toString();
    }

}
