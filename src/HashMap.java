import com.sun.source.tree.BreakTree;

public class HashMap<K, V> {//создаём обобщённый
    //внутри должен жить какой-то массив, при этом элементом массива явл связ список
    private  static final  int INIT_BUCKED_COUNT = 16;//задали константу, которая определяет размер стандартного массива
    private Bucket[] buckets;//это и есть массив, который состоит из бакетов. А бакет это и есть связной список
    /**
     * сущность. Пара ключ и значение
     */
    class Entiti{
        K key;
        V value;
    }


    //создаём БАКЕТ (связный список)

    class Bucket<K, V>{//тоже передаются значения ключ и значение(то, что храниться в элементе массива)



        //так как это список, то у него есть голова
        Node head;//голова списка(это какой-то узел)

        /**
         * узел списка
         */
        class Node{
            /**
             * ссылка на след узел
             */
            Node next;
            /**
             * сама пара значения(ключ и значение)
             */
            Entiti value;
        }

        /**
         * добавляет объект entiti(он хранит в себе ключ и хначение) в бакет(список)
         * @param entiti
         * @return null если entiti первый или ключ нового entiti не совпадает с имеющимися ключами. ИЛИ вернёт предудщее значение по ключу, который уже есть в списке
         */
        public V add(Entiti entiti){
            Node node = new Node();
            node.value = entiti;

            if (head == null){
                head = node;
                return null;
            }
            Node currentNode = head;
            while (true){//проходим по списку и проверяем на равенство ключей
                if(currentNode.value.key.equals(entiti.key)){//если ключи равные, то перезаписываем значения и возвращаем старое значение узла
                    V buf = (V)currentNode.value.value;
                    currentNode.value.value = entiti.value;
                    return buf;
                }
                if(currentNode.next != null){//если прошли по списку и не вышли по return из первого if, то уходим в else
                    currentNode = currentNode.next;
                } else {//если дошли до этого момента, то значит, что в списке нет значений с одинаковыми ключами и добавляем в конец списка node
                    currentNode.next = node;
                    return null;
                }
            }
        }

        /**
         * поиск элемента по ключу. перебирает все элементы последовательно в списке
         * @param key
         * @return
         */
        public V get(K key){
            Node node = head;
            while (node != null){
                if(node.value.key.equals(key)){
                    return (V)node.value.value;
                }
                node = node.next;
            }
            return  null;//если прошли по всему списку и ничего не нашли
        }

        /**
         * получение всех значений из текущего бакета(списка)
         * @return
         */
        public StringBuilder getAllValue(){
            StringBuilder str = new StringBuilder();
            Node node = head;
            while (node != null){
                str.append(node.value.value);
                str.append("\n");
                node = node.next;
            }
            return str;
        }

        /**
         * удаление узла
         * @param key
         * @return
         */
        public V remove(K key){
            if (head == null){
                return null;
            }//вообще в бакетах будет очень редко 2 или более узла. Поэтому возьмём сразу первый же узел - head
            if(head.value.key.equals(key)){
                V buf = (V)head.value.value;//что бы вернуть
                head = head.next;//удаление первого элемента. как это происходит смотри в Notion - алгоритмы - 4 занятие
                return buf;
            }
            else{//если первый узел не содержит такого ключа
                Node node = head;
                while (node.next != null){
                    if(node.next.value.key.equals(key)){
                        V buf = (V)node.next.value.value;
                        node.next = node.next.next;
                        return buf;
                    }
                    node = node.next;
                }
                return null;
            }
        }

    }

    //придумыаем разные кнструкторы. Когда не указываем размер массива - возьмётся стандартный
    //и когда указываем размер массива:
    public HashMap(){
//        buckets = new Bucket[INIT_BUCKED_COUNT];
        //можно воспользоваться перегрузкой
        this(INIT_BUCKED_COUNT);
    }
    public HashMap(int sizeArray){
        buckets = new Bucket[sizeArray];
    }

    /**
     * служебный метод. Высчитывает хеш-код для ключа
     * @param key
     * @return
     */
    private  int calculateBucketIndex(K key){
        int index = key.hashCode() % buckets.length;//это тот случай когда берется остаток от деления хеш-кода на размер массива
        index = Math.abs(index);//это мы взяли по модулю
        return index;
    }

    //добавление элементов в HashMap
    /**
     * ДОбавить новую пару ключ + значение
     * @param key ключ
     * @param value значение
     * @return предыдущее значение(при совпадении ключа, если пытаемся добавить пару с ключом, который уже существует) иначе null
     */
    public V put(K key, V value){
        int index = calculateBucketIndex(key);//определили индекс для нашей пары, которую добавляем
        //так как массив состоит и бакетов(это ссылочный тип данных), значит, что массив до добавления первого бакета
        //на каждой позиции имеет null. Означает, что при нужде добавить пару в массив, нужно проверить, а есть ли уже
        //бакет по такому индексу. И если нет, то создаём бакет и присваеваем его к индексу массива:
        Bucket bucked = buckets[index];//создали для проверки. Из массива buckets по индексу взяли бакет
        if (bucked == null){//если такого бакета под таким индексом в массиве нет, то создаём новый
            bucked = new Bucket();
            buckets[index] = bucked;//заложили бакет в массив
        }

        //теперь создаю объект Entiti, который объединяет ключ и значение
        Entiti entiti = new Entiti();
        entiti.key = key;
        entiti.value = value;
        //именно entiti и является элементом узла (бакета). Необходимо теперь оратиться к бакету по индексу и положить в него entiti

        return (V)bucked.add(entiti);
    }

    /**
     * получение всех значений
     * @return
     */
    public StringBuilder getAllValue(){
        StringBuilder all = new StringBuilder();
        for (Bucket bucket: buckets) {
            if (bucket != null){
                all.append(bucket.getAllValue());
            }
        }
        return all;
    }

    /**
     * поиск по ключу
     * @param key
     * @return
     */
    public V get(K key){
        int index = calculateBucketIndex(key);//определяем сразу код для этого ключа
        Bucket bucked = buckets[index]; //получаем бакет по индексу
        //и если не найдёт такой бакет, то и искать дальше нет смысла. ничего найдена не будет
        if (bucked == null){
            return  null;
        }
        //Ну а если бакет проинициализирован, то внутри лежит либо ноль элементов, либо 1, либо больше одного
        return (V)bucked.get(key);
    }

    /**
     * удаление элемента по ключу
     * @param key
     * @return
     */
    public V remove(K key){
        int index = calculateBucketIndex(key);
        Bucket bucked = buckets[index];
        if (bucked == null){
            return  null;
        }
        return (V)bucked.remove(key);
    }

}
