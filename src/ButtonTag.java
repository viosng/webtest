import org.openqa.selenium.WebElement;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class ButtonTag extends Tag{

    public ButtonTag(WebElement e) {
        super(e);
        d = 0;
        String s;
        if(e.getTagName().equals("details")){
            name = "Подробнее";
        }
        else if (e.getTagName().equals("input")) {
            s = e.getAttribute("value");
            if(s != null){
                name = s;
                if(s.equals("")){
                    d = Integer.MAX_VALUE;
                }
            } else {
                s = e.getAttribute("type");
                if(s.equals("submit")){
                    name = "Отправить";
                } else if(s.equals("reset")) {
                    name = "Изменить";
                } else {
                    d = Integer.MAX_VALUE;
                }
            }
        } else {
            s = e.getText();
            name = s;
            if(s == null){
                d = Integer.MAX_VALUE;
            }
        }
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        if(d == 0) return;
        super.updateDistAndName(name, x, y);
    }

    @Override
    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setValue(String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        if(element.getAttribute("type").equals("image")){
            return "Нажмите на картинку с ссылкой.";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("Нажать кнопку \"");
        buffer.append(name);
        buffer.append("\".");
        return buffer.toString();
    }
}
