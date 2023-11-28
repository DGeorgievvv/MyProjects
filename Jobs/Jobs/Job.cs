using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    public abstract class Job
    {
        private string jobTitle;
        private string companyName;
        private double salary;

        public string JobTitle
        {
            get
            {
                return this.jobTitle;
            }
            set
            {
                if((jobTitle == null || jobTitle == "") || 
                    (jobTitle.Length < 3 || jobTitle.Length > 30))
                {
                    throw new ArgumentException("Job title should be between 3 and 30 characters!");
                }
                this.jobTitle = value;
            }
        }

        public string CompanyName
        {
            get
            {
                return this.companyName;
            }
            set
            {
                if((companyName == null || companyName == "") || 
                    (companyName.Length < 3 || companyName.Length > 30))
                {
                    throw new ArgumentException("Company name should be between 3 and 30 characters!");
                }
                this.companyName = value;
            }
        }

        public double Salary
        {
            get
            {
                return this.salary;
            }
            set
            {
                if(salary < 0)
                {
                    throw new ArgumentException("Salary should be 0 or positive!");
                }
                this.salary = value;
            }
        }

        public Job(string jobTitle, string companyName, double salary)
        {
            this.jobTitle = jobTitle;
            this.companyName = companyName;
            this.salary = salary;
        }

        public override string ToString()
        {
            return $"Job Title: {this.jobTitle}\nCompany: {this.companyName}\n" +
                $"Salary: {this.salary.ToString("f2")} BGN";
        }
    }
}
