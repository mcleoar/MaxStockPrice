import java.util.Scanner;
import java.lang.Math.*;

public class MaxProfitStocks {

        public static void main(String[] args) {
            Scanner input = new Scanner(System.in);

            int numberOfStockPrices = input.nextInt();                    //Read number of buildings from user
            int[] stockPrices = new int[numberOfStockPrices];

            for(int i = 0; i < numberOfStockPrices; i++){                 //Read all building heights
                stockPrices[i] = input.nextInt();
            }

            int[] priceChangeArray = createPriceChangeArray(stockPrices);       //Calculate Array of height changes based on input buildings.

            Jump maxProfit = findMaxProfit(priceChangeArray, 0, priceChangeArray.length-1);       //Initial call the recursive solution

            System.out.print(maxProfit.profit);             //output result

        }

        /**
         * This method will create an array holding the changes in price of the stock between each access to it.
         * @param _stockPrices Array containing a stock's prices over a period of time.
         * @return Array of stock price differences.
         */
        public static int[] createPriceChangeArray(int[] _stockPrices){
            int[] priceChangeArray = new int[_stockPrices.length-1];
            for(int i = 1; i < _stockPrices.length; i++){
                priceChangeArray[i-1] = _stockPrices[i] - _stockPrices[i-1];
            }
            return priceChangeArray;
        }


        /**
         * This method will recursively find the maximum profit achieved by buying and selling stock within the timeframe of the given data.
         * @param _priceChangeArray Array of stock price differences.
         * @param _firstPosition First position of the array or subarray being solved.
         * @param _lastPosition Last position of the array or subarray being solved.
         * @return A Jump object containing where the jump began, ended, and the maximum profit.
         */
        public static Jump findMaxProfit(int[] _priceChangeArray, int _firstPosition, int _lastPosition){
            int middlePosition = (_firstPosition + _lastPosition) / 2;            //initial and floor mid variable.

            if(_firstPosition == _lastPosition)                   //Base Case
                return(new Jump(_firstPosition, _lastPosition, _priceChangeArray[_firstPosition]));
            else{
                Jump leftJump = findMaxProfit(_priceChangeArray, _firstPosition, middlePosition);                //Recursively solve left side of the array
                Jump rightJump = findMaxProfit(_priceChangeArray, middlePosition + 1, _lastPosition);  //Recursively solve right side of the array
                Jump crossJump = findMaxCrossingProfit(_priceChangeArray, _firstPosition, middlePosition, _lastPosition); //Solve for jumps that cross the midpoint of the array

                if(leftJump.profit >= rightJump.profit && leftJump.profit >= crossJump.profit)            //Determine the maximum profit and return it.
                    return leftJump;
                else if(rightJump.profit >= leftJump.profit && rightJump.profit >= crossJump.profit)
                    return rightJump;
                else
                    return crossJump;
            }



        }

        /**
         * This method will test for cases were the max profit could lie across the mid point of the array.
         * @param _priceChangeArray  Array of price differences.
         * @param _firstPosition First position of array or subarray being solved.
         * @param _middlePosition Midpoint between first and last positions (rounded down if not whole integer).
         * @param _lastPosition Last position of array or subarray being solved.
         * @return Jump object containing max profit across the midpoint.
         */
        public static Jump findMaxCrossingProfit(int[] _priceChangeArray, int _firstPosition, int _middlePosition, int _lastPosition){
            int leftProfit = Integer.MIN_VALUE;
            int rightProfit = Integer.MIN_VALUE;
            int totalProfit = 0;
            int startingPosition = 0;
            int endingPosition = 0;

            //Find max profit withing left subarray
            for(int i = _middlePosition; i >= _firstPosition; i--){
                totalProfit += _priceChangeArray[i];
                if(totalProfit > leftProfit){
                    leftProfit = totalProfit;
                    startingPosition = i;
                }
            }

            totalProfit = 0;

            //Find max profit within right subarray
            for(int i = _middlePosition + 1; i <= _lastPosition; i++){
                totalProfit += _priceChangeArray[i];
                if(totalProfit > rightProfit){
                    rightProfit = totalProfit;
                    endingPosition = i;
                }
            }
            //return max Profit across entire array.
            return(new Jump(startingPosition, endingPosition, leftProfit + rightProfit));
        }

    }

