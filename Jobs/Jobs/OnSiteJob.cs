using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    public class OnSiteJob : Job
    {
        private string location;

        public string Location
        {
            get
            {
                return this.location;
            }
            set
            {
                if((location == null || location == "") || 
                    (location.Length < 3 || location.Length > 30))
                {
                    throw new ArgumentException("Location should be between 3 and 30 characters!");
                }
                this.location = value;
            }
        }

        public OnSiteJob(string jobTitle, string companyName, double salary, string location) : 
            base(jobTitle, companyName, salary)
        {
            this.location = location;
        }

        public override string ToString()
        {
            return $"Job Title: {this.JobTitle}\nCompany: {this.CompanyName}\n" +
                $"Salary: {this.Salary} BGN\nLocation: {this.location}";
        }
    }
}
