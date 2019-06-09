public class App {

    // test client
    public static void main(String[] args) {
        SplayTree<Integer, Integer> st1 = new SplayTree<Integer, Integer>();
        st1.put(5, 5);
        st1.put(9, 9);
        st1.put(13, 13);
        st1.put(11, 11);
        st1.put(1, 1);

        SplayTree<String, String> st = new SplayTree<String, String>();
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.13");
        st.put("www.princeton.edu",    "128.112.128.15");
        st.put("www.yale.edu",         "130.132.143.21");
        st.put("www.simpsons.com",     "209.052.165.60");

        //Verificar StdOut:
        /*StdOut.println("The size 0 is: " + st.size());
        st.remove("www.yale.edu");
        StdOut.println("The size 1 is: " + st.size());
        st.remove("www.princeton.edu");
        StdOut.println("The size 2 is: " + st.size());
        st.remove("non-member");
        StdOut.println("The size 3 is: " + st.size());
        StdOut.println(st.get("www.cs.princeton.edu"));
        StdOut.println("The size 4 is: " + st.size());
        StdOut.println(st.get("www.yale.com"));
        StdOut.println("The size 5 is: " + st.size());
        StdOut.println(st.get("www.simpsons.com"));
        StdOut.println("The size 6 is: " + st.size());
        StdOut.println();
    */
    }


}
