import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

//Semana 0 → Preparación para el semestre
//----------------------------------------------------------------------------------------------------------------------
class SuchAlgorithmen <E>{
    //K(String) ist the Key and E is the Element
    private final HashMap<String,E> map;
    public SuchAlgorithmen(){
        this.map = new HashMap<>();
    }
    public SuchAlgorithmen(HashMap<String,E> map){
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
class Generator{
    public static int[] genIntArray(int size){
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * size);
        }
        return array;
    }
}

class PlayGround{
    public static void main(String[] args) {
        int[] si = Generator.genIntArray(100);
        System.out.println(Arrays.toString(SortAlgorithmen.quickSort(si)));
    }
}