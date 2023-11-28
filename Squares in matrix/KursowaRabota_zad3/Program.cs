using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KursowaRabota_zad3
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Enter the size of the matrix: ");
            int n = int.Parse(Console.ReadLine());
            int[,] matrix = new int[n, n];

            for(int i = 0; i < n; i++)
            {
                string[] rowValues = Console.ReadLine().Split();
                for(int j = 0; j < n; j++)
                {
                    matrix[i, j] = int.Parse(rowValues[j]);
                }
            }

            int sum = 0;
            int[] perimeterSum = new int[2 * (n - 1) + 2 * (n - 2)];

            for (int i = 0; i < n - 1; i++)
            {
                for (int j = i; j < n - 1; j++)
                {
                    sum += matrix[i, j];
                    sum += matrix[n - 1 - i, j];
                    if (i != 0 && i != n - 1)
                    {
                        sum += matrix[j, i];
                        sum += matrix[j, n - 1 - i];
                    }
                }
            }

            int index = 0;
            for(int i = 0; i < n - 1; i++)
            {
                for(int j = i; j < n - i; j++)
                {
                    perimeterSum[index++] = sum;
                }
            }

            bool isMonotonic = true;
            for(int i = 1; i < perimeterSum.Length; i++)
            {
                if(perimeterSum[i] < perimeterSum[i - 1])
                {
                    isMonotonic = false;
                    break;
                }
            }

            Console.WriteLine($"The sum of perimeter is : {sum}");

            if(isMonotonic)
            {
                Console.WriteLine("The row is monotonic.");
            }
            else
            {
                Console.WriteLine("The row is not monotonic.");
            }
        }
    }
}
