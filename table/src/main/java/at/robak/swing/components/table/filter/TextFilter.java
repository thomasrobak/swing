/*
 *  GIBA DEVELOPMENT
 */

package at.robak.swing.components.table.filter;

import at.robak.swing.interfaces.IFilter;
import java.util.regex.Pattern;
import javax.swing.RowFilter;

/**
 *
 * @author Thomas Robak
 */
public class TextFilter extends RowFilter implements IFilter {
    private Pattern pattern;

    public TextFilter(String pattern){
        this.pattern = buildPattern(pattern);
    }

    protected static Pattern buildPattern(String input){
        String[] h = input.split(" ");

        StringBuilder sb = new StringBuilder(".*");
        for (String part : h) {
            sb.append(part + ".*");
        }

        return Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
    }

    @Override
    public boolean include(Entry entry) {
        for(int i = 0;i<entry.getValueCount();++i){
            Object value = entry.getValue(i);
            if(value instanceof Object[]){
                if(include((Object[])value)){
                    return true;
                }
            }

            if(include(value)){
                return true;
            }
        }
        return false;
    }

    public boolean include(Object object){
        return object == null ? false : matches(object.toString());
    }

    public boolean include(Object[] lines){
        for (Object line : lines) {
            if(matches(line.toString())){
                return true;
            }
        }
        return false;
    }

    private boolean matches(String text){
        return this.pattern.matcher(text).matches();
    }
}
