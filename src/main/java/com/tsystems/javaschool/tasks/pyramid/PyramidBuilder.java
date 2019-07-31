package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        if (inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }
        int rowCounter = 1;
        int i = 0;
        while (rowCounter <= Integer.MAX_VALUE){
            if (i + rowCounter == inputNumbers.size()) break;
            i = i + rowCounter;
            rowCounter++;
            if (rowCounter == 999){
                throw new CannotBuildPyramidException();
            }
        }
        int columnCounter = rowCounter * 2 - 1;
        //Если использование Коллекций запрещено, то здесь можно использовать пузырьковую сортировку
        Collections.sort(inputNumbers);

        int[][] pyramid = new int[rowCounter][columnCounter];

        //Заполняем нулями
        for (int r = 0; r < rowCounter; r++){
            for (int c = 0; c < columnCounter; c++){
                pyramid[r][c] = 0;
            }
        }

        //Вносим значения из листа
        int listCounter = 0;
        for (int r = 0; r < rowCounter; r++){
            int firstInsertionInRow = rowCounter - r -1;
            int counterOfInsertion = 0;
            while (counterOfInsertion < r + 1){
                pyramid[r][firstInsertionInRow] = inputNumbers.get(listCounter++);
                firstInsertionInRow = firstInsertionInRow + 2;
                counterOfInsertion = counterOfInsertion + 1;
            }
        }



        return pyramid;
    }


}
