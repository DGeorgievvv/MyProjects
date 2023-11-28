using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    class Category
    {
        private string name;
        public List<Job> jobs = new List<Job>();

        public string Name
        {
            get
            {
                return this.name;
            }
            set
            {
                if((name == null || name == "") || 
                    (name.Length < 2 || name.Length > 40))
                {
                    throw new ArgumentException("Name should be between 2 and 40 characters!");
                }
                this.name = value;
            }
        }

        public Category(string name)
        {
            this.name = name;
        }

        public void AddJob(Job job)
        {
            jobs.Add(job);
        }

        public double AverageSalry()
        {
            double sum = 0.0;
            int br = 0;
            foreach(var x in jobs)
            {
                sum += x.Salary;
                br++;
            }
            double avg = sum / br;

            return avg;
        }

        public List<Job> GetOffersAboveSalary(double salary)
        {
            List<Job> aboveThisSalary = new List<Job>();
            foreach(Job x in jobs)
            {
                if(x.Salary >= salary)
                {
                    aboveThisSalary.Add(x);
                }
            }

            if(aboveThisSalary.Count > 0)
            {
                return aboveThisSalary;
            }
            else
            {
                return null;
            }
        }

        public List<Job> GetOffersWithoutSalary()
        {
            List<Job> noSalaryJobs = new List<Job>();
            foreach(Job x in jobs)
            {
                if(x.Salary == 0.0)
                {
                    noSalaryJobs.Add(x);
                }
            }

            if(noSalaryJobs.Count > 0)
            {
                return noSalaryJobs;
            }
            else
            {
                return null;
            }
        }

        public override string ToString()
        {
            return $"Category: {this.name}\nJob Offers: {this.jobs.Count}";
        }
    }
}
