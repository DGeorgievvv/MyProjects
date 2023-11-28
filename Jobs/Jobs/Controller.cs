using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    public class Controller
    {
        List<Category> categories = new List<Category>();
        Category category;

        public void AddCategory(string name)
        {
            category = new Category(name);
            Console.WriteLine($"Created Category: {name}");
        }

        private bool ifContain(List<Category> categories, string name)
        {
            bool check = false;
            if (categories.Any(x => x.Name.Equals(name)))
                check = true;

            return check;
        }

        public void AddJobOffer(string name, string jobTitle, string companyName, double salary, string type)
        {
            if (ifContain(categories, name))
            {
                if (type.Equals("onsite"))
                {
                    Console.WriteLine("Enter location : ");
                    string location = Console.ReadLine();
                    Job job = new OnSiteJob(jobTitle, companyName, salary, location);
                    category.jobs.Add(job);
                    Console.WriteLine($"Created Job Offer: {jobTitle} in Category {name}");
                }
                else if (type.Equals("remote"))
                {
                    bool remote = false;
                    Console.WriteLine("Fully remote? (yes/no) : ");
                    string isRemote = Console.ReadLine();
                    if (isRemote.Equals("yes"))
                        remote = true;
                    Job job = new RemoteJob(jobTitle, companyName, salary, remote);
                    category.jobs.Add(job);
                    Console.WriteLine($"Created Job Offer: {jobTitle} in Category {name}");
                }
            }
            else
            {
                Console.WriteLine("Category not found!");
            }
        }

        public void GetAverageSalary(string name)
        {
            foreach(Category c in categories)
            {
                if(c.Name.Equals(name))
                {
                    Console.WriteLine($"The average salary is {c.AverageSalry()} BGN");
                    return;
                }
            }
            Console.WriteLine("Category not found!");
        }

        public void GetOffersAboveSalary(string name, double salary)
        {
            foreach(Category c in categories)
            {
                if(c.Name.Equals(name))
                {
                    foreach (Job j in c.GetOffersAboveSalary(salary))
                    {
                        Console.WriteLine(j.ToString());
                    }
                }
            }
        }

        public void GetOffersWithoutsalary(string name)
        {
            foreach(Category c in categories)
            {
                if(c.Name.Equals(name))
                {
                    foreach(Job j in c.GetOffersWithoutSalary())
                    {
                        Console.WriteLine(j.ToString());
                    }
                }
            }
        }


    }
}
