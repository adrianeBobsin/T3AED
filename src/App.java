public class App {

    public static void main(String[] args) {
        //Instância árvore de inteiros:
        SplayTree<Integer, Integer> st1 = new SplayTree<Integer, Integer>();

        //Insere 10 elementos:
        st1.put(1, 10);
        st1.put(2, 20);
        st1.put(3, 30);
        st1.put(4, 40);
        st1.put(5, 50);
        st1.put(6, 60);
        st1.put(7, 70);
        st1.put(8, 80);
        st1.put(9, 90);
        st1.put(10, 100);

        // Verificar se a árvore está balanceada:
            // Implementar

        //Verificar a altura da árvore:
        System.out.println("Altura da árvore: "+ st1.height());

        //Lista de 

        //Exibir árvore com métodos de caminhamento:
            //Implementar

    }
}
