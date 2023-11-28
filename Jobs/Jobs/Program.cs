using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    class Program
    {
        static void Main(string[] args)
        {
            Controller controller = new Controller();
            string categoryName;
            double salary;
            Console.WriteLine("Enter Command : ");
            string input = Console.ReadLine();
            while(!(input.Equals("End")))
            {
                switch(input)
                {
                    case "AddCategory":
                        Console.WriteLine("Enter category name : ");
                        categoryName = Console.ReadLine();
                        controller.AddCategory(categoryName);
                        break;
                    case "AddJobOffer":
                        Console.WriteLine("Enter job category : ");
                        categoryName = Console.ReadLine();
                        Console.WriteLine("Enter job title : ");
                        string jobTitle = Console.ReadLine();
                        Console.WriteLine("Enter company name : ");
                        string companyName = Console.ReadLine();
                        Console.WriteLine("Enter salary : ");
                        salary = double.Parse(Console.ReadLine());
                        Console.WriteLine("Enter type of job(remote/insite) : ");
                        string typeOfJob = Console.ReadLine();
                        controller.AddJobOffer(categoryName, jobTitle, companyName,
                            salary, typeOfJob);
                        break;
                    case "GetAverageSalry":
                        Console.WriteLine("Enter categoy name : ");
                        categoryName = Console.ReadLine();
                        controller.GetAverageSalary(categoryName);
                        break;
                    case "GetOffersAboveSalary":
                        Console.WriteLine("Enter caegory name : ");
                        categoryName = Console.ReadLine();
                        Console.WriteLine("Enter salary for comparing : ");
                        salary = double.Parse(Console.ReadLine());
                        controller.GetOffersAboveSalary(categoryName, salary);
                        break;
                    case "GetOffersWithoutSalary":
                        Console.WriteLine("Enter caegory name : ");
                        categoryName = Console.ReadLine();
                        controller.GetOffersWithoutsalary(categoryName);
                        break;
                    default:
                        throw new ArgumentException("Invalid input for caommand!");
                }

                input = Console.ReadLine();
            }
        }
    }
}
