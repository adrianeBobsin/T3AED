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
     *  Notação O()
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
     * Notação O()
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
        // splay key to root
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
            root.left.father = root;
            root.right.father = root;
        }

        // Insert new node at root
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
            root.left.father = root;
        }

        // It was a duplicate key. Simply replace the value
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
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
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
            // key not in tree, so we're done
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
     * Notação O()
     *
     * @param n
     * @return pai do elemento
     */
    public int getParent(Integer n) {
        Node aux = searchNodeRef(n, root);
        return aux.father.value;
    }

    /**
     * Método que verifica se a árvore é balanceada
     * Notação O()
     *
     * @return true se a árvore for balanceada ou
     * false se não for.
     */
  //  private boolean isBalanced() {
       // Implementar
   // }


    /***************************************************************************
     *      Métodos de caminhamento                                            *
     ***************************************************************************/

    /**
     * Caminhamento pré-fixado
     * Notação O()
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
     * Notação O()
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
     * Notação O()
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



    /***************************************************************************
     *      Métodos auxiliares                                                 *
     ***************************************************************************/

    // height of tree (1-node tree has height 0)
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.father = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.father = h;
        return x;
    }
}