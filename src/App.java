public class App {

    public static void main(String[] args) {
        //Instância árvore de inteiros:
        SplayTree<Integer, Integer> st1 = new SplayTree<Integer, Integer>();

        //Insere 10 elementos:
        st1.put(1, 10);
        st1.put(2, 20);
        st1.put(3, 30);
        st1.put(4, 60);
        st1.put(5, 40);
        st1.put(6, 55);
        st1.put(7, 75);
        st1.put(8, 80);
        st1.put(9, 15);
        st1.put(10, 25);

        // Verificar se a árvore está balanceada:
            // Implementar


        //Verificar a altura da árvore:
        System.out.println("Altura da árvore: "+ st1.height());


        /***************************************************************************
         *      Caminhamentos                                                      *
         ***************************************************************************/

        // == Pré-fixado ==
        System.out.println("Caminhamento pré-fixado: \n"+ st1.positionsPre());

        // == Pós-fixado ==
        System.out.println("Caminhamento pós-fixado: \n"+st1.positionsPos());

        // == Central ==
        System.out.println("Caminhamento central: \n"+st1.positionsCentral());

        // == Largura ==
        System.out.println("Caminhamento por largura: \n"+st1.positionsWidth());
    }
}
