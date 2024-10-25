import java.util.Random;

public class MergeSort {
    public static void main(String[] args) {

        Random rand = new Random();
        int[] numbers = new int[10];

        // Generate random numbers
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(100);
        }

        System.out.println("Before: ");
        printArray(numbers);

        // Perform merge sort
        mergeSort(numbers);

        System.out.println("After: ");
        printArray(numbers);
    }

    // Merge sort method
    private static void mergeSort(int[] inputArray) {
        int inputLength = inputArray.length;

        // Base case: array is of length 1 or smaller
        if (inputLength < 2) {
            return;
        }

        // Split the array into two halves
        int midIndex = inputLength / 2;
        int[] leftArray = new int[midIndex];
        int[] rightArray = new int[inputLength - midIndex];

        for (int i = 0; i < midIndex; i++) {
            leftArray[i] = inputArray[i];
        }

        for (int i = midIndex; i < inputLength; i++) {
            rightArray[i - midIndex] = inputArray[i];
        }

        // Recursively sort both halves
        mergeSort(leftArray);
        mergeSort(rightArray);

        // Merge the sorted halves
        merge(inputArray, leftArray, rightArray);
    }

    // Merge two sorted arrays
    private static void merge(int[] resultArray, int[] leftArray, int[] rightArray) {
        int leftSize = leftArray.length;
        int rightSize = rightArray.length;
        int i = 0, j = 0, k = 0;

        // Merge elements from both arrays into resultArray
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                resultArray[k++] = leftArray[i++];
            } else {
                resultArray[k++] = rightArray[j++];
            }
        }

        // If there are remaining elements in the left array, add them
        while (i < leftSize) {
            resultArray[k++] = leftArray[i++];
        }

        // If there are remaining elements in the right array, add them
        while (j < rightSize) {
            resultArray[k++] = rightArray[j++];
        }
    }

    // Method to print the array
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
