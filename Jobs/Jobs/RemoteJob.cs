using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Jobs
{
    public class RemoteJob : Job
    {
        private bool fullyRemote;

        public bool FullyRemote
        {
            get
            {
                return this.fullyRemote;
            }
            set
            {
                this.fullyRemote = false;
            }
        }

        public RemoteJob(string jobTitle, string companyName, double salary, bool fullyRemote) : 
            base(jobTitle, companyName, salary)
        {
            this.fullyRemote = fullyRemote;
        }

        public override string ToString()
        {
            if(this.fullyRemote == true)
            {
                return $"Job Title: {this.JobTitle}\nCompany: {this.CompanyName}\n" +
                    $"Salary: {this.Salary} BGN\nFullyRemote: Yes";
            }
            else
            {
                return $"Job Title: {this.JobTitle}\nCompany: {this.CompanyName}\n" +
                    $"Salary: {this.Salary} BGN\nFullyRemote: No";
            }
        }
    }
}
