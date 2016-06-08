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
    public class EventsController : Controller
    {
        private readonly AppDbContext _context;

        public EventsController(AppDbContext context)
        {
            this._context = context;
        }

        // GET: api/events
        [HttpGet]
        public IEnumerable<AppEvent> Get()
        {
            return _context.HumanitaryEvents
                .Include(u => u.UserOwner)
                .Include(c => c.Comments);
        }

        // GET api/events/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var humEvent = _context.HumanitaryEvents
                .Include(u => u.UserOwner)
                .Include(a => a.UsersWhoAttending)
                .FirstOrDefault(d => d.Id == id);

            if (humEvent != null)
            {
                return new ObjectResult(humEvent);
            }
            else
            {
                return new HttpNotFoundResult();
            }
        }

        [HttpGet("owner/{ownerId}")]
        public IEnumerable<AppEvent> GetEventsByOwner(int ownerId)
        {
            return _context.HumanitaryEvents
               .Include(c => c.Comments)
               .Include(u=> u.UserOwner)
               .Include(a=> a.UsersWhoAttending)
               .Where(d => d.UserOwnerId == ownerId);

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
