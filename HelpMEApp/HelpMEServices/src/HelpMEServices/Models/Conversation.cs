using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace HelpMEServices.Models
{
    public class Conversation
    {
        public int Id { get; set; }
        public int User1Id { get; set; }
        public int User2Id { get; set; }

        public List<Message> Messages { get; set; }
        
    }
}
