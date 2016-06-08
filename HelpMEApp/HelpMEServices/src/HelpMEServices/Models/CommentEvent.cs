using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class CommentEvent : Comment
    {

        public int EventID { get; set; }
        public AppEvent Event { get; set; }
    }
}
