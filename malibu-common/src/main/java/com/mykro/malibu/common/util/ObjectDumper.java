package com.mykro.malibu.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;

/***
 * @author John Rix
 * 
 * Generates a String that contains a recursive dump of an object hierarchy.
 * 
 * - Can control max recursion depth
 * - Can limit array elements output
 * - Can exclude any list of classes, fields, or class+field combinations from output - just pass an array with any combination of class names, classname+fieldname pairs separated with a colon, or fieldnames with a colon prefix
 * - Will not output the same object twice (the output indicates when an object was previously visited and provides the hashcode for correlation) - this avoids circular references causing problems
 * 
 * You can call this using one of the two methods below:
 *     String dump = Dumper.dump(myObject);
 *     String dump = Dumper.dump(myObject, maxDepth, maxArrayElements, ignoreList);
 *     
 * As mentioned above, you need to be careful of stack-overflows with this, so use the max recursion depth facility to minimise the risk.
 * 
 * This code is released to the public domain.
 * 
 * @see http://stackoverflow.com/questions/603013/dumping-a-java-objects-properties
 */
@SuppressWarnings(value = { "all" })
public class ObjectDumper {

    private static ObjectDumper instance = new ObjectDumper();

    protected static ObjectDumper getInstance() {
        return instance;
    }

    class DumpContext {
        int maxDepth = 0;
        int maxArrayElements = 0;
        int callCount = 0;
        HashMap<String, String> ignoreList = new HashMap<String, String>();
        HashMap<Object, Integer> visited = new HashMap<Object, Integer>();
    }

    public static String dump(Object o) {
        return dump(o, 0, 0, null);
    }

    public static String dump(Object o, int maxDepth, int maxArrayElements, String[] ignoreList) {
        DumpContext ctx = getInstance().new DumpContext();
        ctx.maxDepth = maxDepth;
        ctx.maxArrayElements = maxArrayElements;

        if (ignoreList != null) {
            for (int i = 0; i < Array.getLength(ignoreList); i++) {
                int colonIdx = ignoreList[i].indexOf(':');
                if (colonIdx == -1)
                    ignoreList[i] = ignoreList[i] + ":";
                ctx.ignoreList.put(ignoreList[i], ignoreList[i]);
            }
        }

        return dump(o, ctx);
    }

    protected static String dump(Object o, DumpContext ctx) {
        if (o == null) {
            return "<null>";
        }

        ctx.callCount++;
        StringBuffer tabs = new StringBuffer();
        for (int k = 0; k < ctx.callCount; k++) {
            tabs.append("\t");
        }
        StringBuffer buffer = new StringBuffer();
        Class oClass = o.getClass();

        String oSimpleName = getSimpleNameWithoutArrayQualifier(oClass);

        if (ctx.ignoreList.get(oSimpleName + ":") != null)
            return "<Ignored>";

        if (oClass.isArray()) {
            buffer.append("\n");
            buffer.append(tabs.toString().substring(1));
            buffer.append("[\n");
            int rowCount = ctx.maxArrayElements == 0 ? Array.getLength(o) : Math.min(ctx.maxArrayElements, Array.getLength(o));
            for (int i = 0; i < rowCount; i++) {
                buffer.append(tabs.toString());
                try {
                    Object value = Array.get(o, i);
                    buffer.append(dumpValue(value, ctx));
                } catch (Exception e) {
                    buffer.append(e.getMessage());
                }
                if (i < Array.getLength(o) - 1)
                    buffer.append(",");
                buffer.append("\n");
            }
            if (rowCount < Array.getLength(o)) {
                buffer.append(tabs.toString());
                buffer.append(Array.getLength(o) - rowCount + " more array elements...");
                buffer.append("\n");
            }
            buffer.append(tabs.toString().substring(1));
            buffer.append("]");
        } else {
            buffer.append("\n");
            buffer.append(tabs.toString().substring(1));
            buffer.append("{\n");
            buffer.append(tabs.toString());
            buffer.append("hashCode: " + o.hashCode());
            buffer.append("\n");
            while (oClass != null && oClass != Object.class) {
                Field[] fields = oClass.getDeclaredFields();

                if (ctx.ignoreList.get(oClass.getSimpleName()) == null) {
                    if (oClass != o.getClass()) {
                        buffer.append(tabs.toString().substring(1));
                        buffer.append("  Inherited from superclass " + oSimpleName + ":\n");
                    }

                    for (int i = 0; i < fields.length; i++) {

                        String fSimpleName = getSimpleNameWithoutArrayQualifier(fields[i].getType());
                        String fName = fields[i].getName();

                        fields[i].setAccessible(true);
                        buffer.append(tabs.toString());
                        buffer.append(fName + "(" + fSimpleName + ")");
                        buffer.append("=");

                        if (ctx.ignoreList.get(":" + fName) == null &&
                            ctx.ignoreList.get(fSimpleName + ":" + fName) == null &&
                            ctx.ignoreList.get(fSimpleName + ":") == null) {

                            try {
                                Object value = fields[i].get(o);
                                buffer.append(dumpValue(value, ctx));
                            } catch (Exception e) {
                                buffer.append(e.getMessage());
                            }
                            buffer.append("\n");
                        }
                        else {
                            buffer.append("<Ignored>");
                            buffer.append("\n");
                        }
                    }
                    oClass = oClass.getSuperclass();
                    oSimpleName = oClass.getSimpleName();
                }
                else {
                    oClass = null;
                    oSimpleName = "";
                }
            }
            buffer.append(tabs.toString().substring(1));
            buffer.append("}");
        }
        ctx.callCount--;
        return buffer.toString();
    }

    protected static String dumpValue(Object value, DumpContext ctx) {
        if (value == null) {
            return "<null>";
        }
        if (value.getClass().isPrimitive() ||
            value.getClass() == java.lang.Short.class ||
            value.getClass() == java.lang.Long.class ||
            value.getClass() == java.lang.String.class ||
            value.getClass() == java.lang.Integer.class ||
            value.getClass() == java.lang.Float.class ||
            value.getClass() == java.lang.Byte.class ||
            value.getClass() == java.lang.Character.class ||
            value.getClass() == java.lang.Double.class ||
            value.getClass() == java.lang.Boolean.class ||
            value.getClass() == java.util.Date.class ||
            value.getClass().isEnum()) {

            return value.toString();

        } else {

            Integer visitedIndex = ctx.visited.get(value);
            if (visitedIndex == null) {
                ctx.visited.put(value, ctx.callCount);
                if (ctx.maxDepth == 0 || ctx.callCount < ctx.maxDepth) {
                    return dump(value, ctx);
                }
                else {
                    return "<Reached max recursion depth>";
                }
            }
            else {
                return "<Previously visited - see hashCode " + value.hashCode() + ">";
            }
        }
    }


    private static String getSimpleNameWithoutArrayQualifier(Class clazz) {
        String simpleName = clazz.getSimpleName();
        int indexOfBracket = simpleName.indexOf('['); 
        if (indexOfBracket != -1)
            return simpleName.substring(0, indexOfBracket);
        return simpleName;
    }
}
