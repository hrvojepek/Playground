using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNet.Mvc;
using HelpMEServices.Models;
using Microsoft.Data.Entity;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace HelpMEServices.Controllers
{
    [Route("api/[controller]")]
    public class ConversationsController : Controller
    {
        private readonly AppDbContext _context;

        public ConversationsController(AppDbContext context)
        {
            this._context = context;
        }

        // GET: api/conversations
        [HttpGet]
        public IEnumerable<Conversation> Get()
        {
            return _context.Conversations.Include(m=>m.Messages);
        }

        // GET api/conversations/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var conversation = _context.Conversations
                .Include(m => m.Messages)
               .FirstOrDefault(d => d.Id == id);

            if (conversation != null)
            {
                return new ObjectResult(conversation);
            }
            else
            {
                return new HttpNotFoundResult();
            }
        }

        // GET api/conversations/5
        [HttpGet("userid/{id}")]
        public IActionResult GetByUserId(int id)
        {
            var conversation = _context.Conversations
                .Include(m=>m.Messages)
               .Where(d => d.User1Id == id || d.User2Id == id);

            if (conversation != null)
            {
                return new ObjectResult(conversation);
            }
            else
            {
                return new HttpNotFoundResult();
            }
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
