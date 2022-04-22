import java.util.*;

//Semana 0 → Preparación para el semestre
//----------------------------------------------------------------------------------------------------------------------
class SearchAlgorithm <E>{
    //K(String) ist the Key and E is the Element
    private final HashMap<String,E> map;
    public SearchAlgorithm(){
        this.map = new HashMap<>();
    }
    public SearchAlgorithm(HashMap<String,E> map){
        this.map = map;
    }
    //..................................Sequenzielle Suche: linear und einfach..........................................
    public E linearSearchIterative(String[] arr, String goal){
        for (String t : arr) {
            if (t == goal) {
                return map.get(goal);
            }
        }
        throw new NoSuchElementException("Element could not be found");
    }
    public E linearSearchRecursive(String[] arr, String goal){
        return linearSearchRecursive(arr,goal,0);
    }
    private E linearSearchRecursive(String[] array, String goal, int i){
        if (Objects.equals(array[i], goal)){
            return map.get(goal);
        }
        if(i == array.length - 1){
            throw new NoSuchElementException("Element could not be found");
        }
        return linearSearchRecursive(array,goal,i+1);
    }
    //...................................Binäre Suche: logarithmic und wirkungsvoll.....................................
    public E binarySearchIterativ(String[] arr, String goal){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int middle = (left + right)/2;
            if(Objects.equals(arr[middle], goal)){
                return map.get(goal);
            }
            if(arr[middle].compareTo(goal) > 0){
                right = middle - 1;
            }
            else if(arr[middle].compareTo(goal) < 0){
                left = middle + 1;
            }
        }
        throw new NoSuchElementException("Element could not be found");
    }
    public E binarySearchRecursive(String[] arr, String goal){
        return binarySearchRecursive(arr,goal,0,arr.length -1);
    }
    public E binarySearchRecursive(String[] arr, String goal, int left, int right){
        if(left > right){
            throw new NoSuchElementException("Element could not be found");
        }
        int middle = (left + right)/2;
        if(Objects.equals(arr[middle],goal)){
            return map.get(goal);
        }
        if(arr[middle].compareTo(goal) > 0){
            return binarySearchRecursive(arr,goal,left,middle-1);
        }
        else if(arr[middle].compareTo(goal) < 0){
            return binarySearchRecursive(arr,goal,middle+1,right);
        }
        throw new NoSuchElementException("Element could not be found");
    }
}
//----------------------------------------------------------------------------------------------------------------------
class SortAlgorithmen {
    //wer sortiert, findet schneller
    //................................BubbleSort: quadratisch und nicht schwierig.......................................
    public static int[] bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
        return arr;
    }

    //................................MergeSort: linearLogarithmic und etwas komplexer..................................
    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }
        int[] left;
        int[] right;
        if (arr.length % 2 == 0) {
            left = new int[arr.length / 2];
        } else {
            left = new int[arr.length / 2 + 1];
        }
        right = new int[arr.length / 2];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        int k = 0;
        for (int i = left.length; i < arr.length; i++) {
            right[k++] = arr[i];
        }
        return merge(mergeSort(left), mergeSort(right));
    }

    private static int[] merge(int[] left, int[] right) {
        int i = 0;      //index for left
        int j = 0;      //index for right
        int k = 0;      //index for returned
        int[] merged = new int[left.length + right.length];
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }
        while (i < left.length) {
            merged[k++] = left[i++];
        }
        while (j < right.length) {
            merged[k++] = right[j++];
        }
        return merged;
    }
    //..............................QuickSort: linearLogarithmic und genauso komplexer..................................
    public static int[] quickSort(int[] arr){
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    private static void quickSort(int[] arr, int left, int right){
        if(left < right){
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot-1);
            quickSort(arr, pivot+1, right);
        }
    }
    private static int partition(int[] arr, int left, int right){
        int pivot = arr[right];
        int i = left - 1;           //index of smallest Element
        for (int j = left; j < right ; j++) {
            if(arr[j] <= pivot){    //if current Element is smaller than or equal to pivot
                i++;
                int tmp = arr[i];   //swap arr[i] with arr[j]
                arr[i] = arr [j];
                arr[j] = tmp;
            }
        }                           // swap arr[i+1] and arr[high] (or pivot)
        int tmp = arr[i+1];
        arr[i+1] = arr[right];
        arr[right] = tmp;
        return i + 1;
    }
}
//----------------------------------------TopSort: lets see what's coming next------------------------------------------
//-------Topological Sort sortiert eine Liste von Knoten, indem er die Ordnung dieser Kannten analysiert und die--------
//-------entsprechend ihrer Grösse aufsteigend sortiert.----------------------------------------------------------------
class TopologicalSort{
    private int numberOfVertices;
    private LinkedList<Integer>[] adjacencyList;
    public TopologicalSort(int numberOfVertices){
        this.numberOfVertices = numberOfVertices;
        adjacencyList = new LinkedList[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
    }
    public void addEdge(int origin, int destiny){
        adjacencyList[origin].add(destiny);
    }
    public void topSortIntern(int vertices, boolean[] visited , Stack<Integer> stack){
        visited[vertices] = true;
        for (Integer integer : adjacencyList[vertices]) {
            if (!visited[integer]) {
                topSortIntern(integer, visited, stack);
            }
        }
        stack.push(vertices);
    }
    public void topologicalSort(){
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            if(!visited[i]){
                topSortIntern(i,visited,stack);
            }
        }
        while (!stack.empty()){
            System.out.println(stack.pop() + "");
        }
    }
    public static void main(String[] args) {
        TopologicalSort g = new TopologicalSort(6);
        g.addEdge(5, 2);
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        System.out.println("Following is a Topological " +
                "sort of the given graph");
        g.topologicalSort();
    }
}
//----------------------------------------------------------------------------------------------------------------------
class SearchAlgorithmForText{
    public static void isInTextEinfach(String text, String word){
        boolean somethingFound = false;
        for (int pos = 0; pos <= text.length() - word.length(); pos++) {
            for (int j = word.length() - 1 ; j >= 0 && word.charAt(j) == text.charAt(pos + j); j--) {
                if (j == 0){
                    System.out.println("Word is in Position: " + pos);
                    somethingFound = true;
                }
            }
        }
        if(!somethingFound){
            throw new NoSuchElementException("Word is not in Text");
        }
    }
    public static void isInText(String text,String word){

    }
    public static void main(String[] args) {
        String text = "Such die Nadel im heu.";
        String query = "die";
        isInTextEinfach(text,query);
    }
}
//----------------------------------------------------------------------------------------------------------------------
class Generator{
    public static int[] genIntArray(int size){
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * size);
        }
        return array;
    }
}
//----------------------------------------------------------------------------------------------------------------------
class PlayGround{
    public static void main(String[] args) {

    }
}
/*
private Stack<Node<T>> sortedStack;
    static class Node<T>{
        private final T data;
        boolean visited;
        List<Node<T>> neighbours;
        public Node(T data){
            this.data = data;
            neighbours = new ArrayList<>();
            visited = false;
        }

        public List<Node<T>> getNeighbours() {
            return neighbours;
        }

        public T getData() {
            return data;
        }

        public void addNeighbours(Node<T> neighbour){
            if(Objects.equals(neighbour,null)){
                throw new NoSuchElementException("neighbour should exist");
            }
            if(neighbour.getData().equals(data)){
                return;
            }
            this.neighbours.add(neighbour);
        }

        public void setNeighbours(List<Node<T>> neighbours) {
            this.neighbours = neighbours;
        }
        public String showNeighbourHood(){
            StringBuilder group = new StringBuilder("( " + data + " ) -> { ");
            for (Node<T> e: neighbours) {
                group.append(e.toString()).append(", ");
            }
            group.append(" }");
            return group.toString();

        }
        public String toString(){
            return "" + data;
        }
    }
    public TopologicalSort(){
        this.sortedStack = new Stack<>();
    }
    public void topologicalSort(Node<T> node){
        List<Node<T>> neighbours = node.getNeighbours();
        for (Node<T> n : neighbours) {
            if (!n.visited) {
                topologicalSort(n);
                n.visited = true;
            }
        }
        sortedStack.push(node);
    }

    public Stack<Node<T>> getSortedStack() {
        return sortedStack;
    }

    public static void main(String[] args) {
        TopologicalSort<Integer> topSort = new TopologicalSort<>();
        Node<Integer> n1 = new Node<>(1);
        Node<Integer> n2 = new Node<>(2);
        Node<Integer> n3 = new Node<>(3);
        Node<Integer> n4 = new Node<>(4);
        Node<Integer> n5 = new Node<>(5);
        //we add neighbours
        n1.addNeighbours(n4);
        n1.addNeighbours(n5);
        n3.addNeighbours(n2);
        n4.addNeighbours(n2);
        n5.addNeighbours(n4);
        topSort.topologicalSort(n1);
        Stack<Node<Integer>> sortedStack = topSort.getSortedStack();
        while (!sortedStack.empty()){
            System.out.println(sortedStack.pop()+"");
        }
    }
 */