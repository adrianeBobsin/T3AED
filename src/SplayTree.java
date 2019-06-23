/**
 * Splay Tree
 *
 * @author Adriane, Matheus e Pércio
 */

public class SplayTree<Key extends Comparable<Key>, Value> {

    //Raiz da árvore
    private Node root;

    // === Classe Node ===
    private class Node {
        private Key key;            // Chave do nodo
        private Integer value;      // Valor
        private Node left, right;   // Referências de esquerda e direita do nodo
        private Node father;        // Referência para o pai do nodo

        // == Constructor Node ==
        public Node(Key key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Verifica se a chave passada por parâmetro
     *  existe na árvore.
     *  Notação O(n)
     *
     * @param key
     * @return true se a chave existe ou false se não existir na árvore
     */
    public boolean contains(Key key) {
        return get(key) != 0;
    }

    /**
     * Verifica se a árvore esta vazia.
     * Notação O(n)
     *
     * @return true se a árvore estiver vazia ou
     * false se tiver ao menos um elemento.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Pega um valor através da chave passada por parâmetro.
     * Notação O(n)
     *
     * @param key
     * @return o valor do nodo que possui a chave indicada ou null se a
     * chave não existir na árvore.
     */
    public int get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return 0;
    }

    /**
     * Método que remove o nodo com chave passada por parâmetro da árvore.
     * Notação O(log n)
     *
     * @param key
     */
    public void remove(Key key) {
        if (root == null) return; // árvore vazia

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }

        // Se compareTo retornar valor != 0, o valor não esta na árvore
    }

    /**
     * Método de inserção de um novo elemento na árvore.
     * Notação O(log n)
     *
     * @param key
     * @param value
     */
    public void put(Key key, int value) {
        // Se a árvore estiver vazia,
        // inclui o elemento na raiz
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insere o novo nodo sempre na raiz
        // mas antes verifica se a raiz atual da árvore
        // é maior ou menor que o novo nodo que está sendo inserido
        // para decidir de qual a antiga raiz será alocada como filha do novo nodo.
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
            root.left.father = root;
            root.right.father = root;
        }
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
            root.left.father = root;
        }

        // Se for um valor duplicado, apenas substitui o valor da raiz
        else {
            root.value = value;
        }
    }

    /**
     * Método de espelhamento da árvore.
     * Notação O(log n)
     *
     * @param h
     * @param key
     * @return o nodo pesquisado
     */
    private Node splay(Node h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            //Se o valor não existe na árvore retorna ele mesmo
            if (h.left == null) {
                return h;
            }

            // Se o valor existe na árvore
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            } else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else return rotateRight(h);
        } else if (cmp1 > 0) {
            //Se o valor não existe na árvore retorna ele mesmo
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            } else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else return rotateLeft(h);
        } else return h;
    }

    /**
     * Método que retorna a referencia do nodo que possui o valor
     * passado como parâmetro.
     * Notação O(log n)
     *
     * @param element e target
     * @return referencia do nodo
     */
    private Node searchNodeRef(Integer element, Node target) {
        int r;

        if (element == null || target == null) {
            return null;
        }

        r = target.value.compareTo(element);

        if (r == 0) {
            return target;
        } else if (r > 0) {
            return searchNodeRef(element, target.left);
        } else {
            return searchNodeRef(element, target.right);
        }
    }


    /**
     * Método que retorna o pai do elemento.
     * Notação O(n)
     *
     * @param n
     * @return pai do elemento
     */
    public int getParent(Integer n) {
        Node aux = searchNodeRef(n, root);
        int parent = aux.father.value;
        root = splay(root, aux.father.key);
        return parent;
    }

    /**
     * Método que verifica se a árvore é balanceada
     * Notação O(n)
     *
     * @return true se a árvore for balanceada ou
     * false se não for.
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node root) {
        if (root == null) {
            return true;
        }

        return (Math.abs(height(root.left) - height(root.right)) < 2)
                && isBalanced(root.left) && isBalanced(root.right);
    }

    /**
     * Método que retorna a altura da árvore.
     * Notação O(n)
     *
     * @return height
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Método que retorna o tamanho da árvore.
     * Notação O(n)
     *
     * @return
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }


    /**
     * Método que realiza a rotação dos nodos à direita.
     * É utilizado pelo metodo Splay.
     *
     * Notação O(n)
     *
     * @param h
     * @return o nodo alterado de posição
     */
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.father = h;
        return x;
    }


    /**
     * Método que realiza a rotação dos nodos à esquerda.
     * É utilizado pelo metodo Splay.
     *
     * Notação O(n)
     *
     * @param h
     * @return o nodo alterado de posição
     */
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.father = h;
        return x;
    }


    /***************************************************************************
     *      Métodos de caminhamento                                            *
     ***************************************************************************/

    /**
     * Caminhamento pré-fixado
     * Notação O(n)
     *
     * @return uma lista de inteiros contendo os elementos da árvore.
     */
    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }
    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.value); //Visita o nodo
            positionsPreAux(n.left, res); //Visita a subarvore esquerda
            positionsPreAux(n.right, res); //Visita a subarvore direita
        }
    }

    /**
     * Caminhamento pós-fixado
     * Notação O(n)
     *
     * @return uma lista de inteiros contendo os elementos da árvore.
     */
    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPosAux(root, res);
        return res;
    }
    private void positionsPosAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsPosAux(n.left, res); //Visita a subarvore esquerda
            positionsPosAux(n.right, res); //Visita a subarvore direita
            res.add(n.value); //Visita o nodo
        }
    }

    /**
     * Caminhamento central.
     * Notação O(n)
     *
     * @return lista de inteiros contendo os elementos da árvore.
     */
    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.left, res); //Visita a subarvore esquerda
            res.add(n.value); //Visita o nodo
            positionsCentralAux(n.right, res); //Visita a subarvore direita
        }
    }

    /**
     * Retorna uma lista com todos os elementos da árvore na ordem de
     * caminhamento em largura.
     *
     * Notação O(n)
     *
     * @return LinkedListOfInteger lista com os elementos da arvore
     */
    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        LinkedListOfInteger res = new LinkedListOfInteger();
        fila.enqueue(root);
        positionsWidthAux(root, res, fila);
        return res;
    }

    private void positionsWidthAux(Node n, LinkedListOfInteger res, Queue<Node> fila) {
        if (n != null) {
            if (!fila.isEmpty()) {
                // Incluir os nodos filhos na fila
                if (n.right != null) {
                    fila.enqueue(n.right);
                }

                if (n.left != null) {
                    fila.enqueue(n.left);
                }

                //Pega o nodo que esta sendo visitado e inclui na lista
                res.add(n.value);

                //exclui esse elemento visitado da fila
                fila.dequeue();

                //Chama o próprio método aux passando o
                //primeiro elemento da fila.
                if (!fila.isEmpty()) {
                    positionsWidthAux(fila.head(), res, fila);
                }

            }
        }
    }

}