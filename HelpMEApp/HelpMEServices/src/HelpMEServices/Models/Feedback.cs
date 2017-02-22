using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Feedback
    {
        public int Id { get; set; }
        public string UserPhoto { get; set; }
        public string UserName { get; set; }
        public string FeedbackComment { get; set; }
    }
}
