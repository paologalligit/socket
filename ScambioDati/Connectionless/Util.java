public class Util {
  public static void print(Boolean flag, Object... s) {
    for (Object o : s) {
        System.out.print(o);
    }

    if (flag)
        System.out.println();
    else
        System.out.print("");
}
}