import java.util.Arrays;

public class RadixSort {

    public static void radixSort(int[] numbers) {
        int maximumNumber = findMaximumNumberIn(numbers);
        int numberOfDigits = calculateNumberOfDigitsIn(maximumNumber);
        int placeValue = 1;
        while (numberOfDigits-- > 0) {
            applyCountingSortOn(numbers, placeValue);
            placeValue *= 10;
        }
    }

    // Helper function to find the maximum number
    private static int findMaximumNumberIn(int[] numbers) {
        int max = numbers[0];
        for (int num : numbers) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // Helper function to calculate the number of digits in the maximum number
    private static int calculateNumberOfDigitsIn(int number) {
        return (int) Math.log10(number) + 1;
    }

    // The counting sort function applied on a specific digit (place value)
    public static void applyCountingSortOn(int[] numbers, int placeValue) {
        int length = numbers.length;
        int range = 10;  // decimal system, digits from 0 to 9
        int[] frequency = new int[range];
        int[] sortedValues = new int[length];

        // Calculate the frequency of digits
        for (int i = 0; i < length; i++) {
            int digit = (numbers[i] / placeValue) % range;
            frequency[digit]++;
        }

        // Calculate cumulative frequency
        for (int i = 1; i < range; i++) {
            frequency[i] += frequency[i - 1];
        }

        // Sort the numbers based on the current digit
        for (int i = length - 1; i >= 0; i--) {
            int digit = (numbers[i] / placeValue) % range;
            sortedValues[frequency[digit] - 1] = numbers[i];
            frequency[digit]--;
        }

        // Copy the sorted values back to the original array
        System.arraycopy(sortedValues, 0, numbers, 0, length);
    }

    // Testing the radix sort
    public static void main(String[] args) {
        int[] numbers = {783, 99, 472, 182, 264, 543, 356, 295, 692, 491, 94};
        System.out.println("Original array: " + Arrays.toString(numbers));

        radixSort(numbers);

        System.out.println("Sorted array: " + Arrays.toString(numbers));
    }
}
