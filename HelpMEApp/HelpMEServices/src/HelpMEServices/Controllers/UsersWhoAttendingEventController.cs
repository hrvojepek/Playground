using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNet.Mvc;
using HelpMEServices.Models;

// For more information on enabling Web API for empty projects, visit http://go.microsoft.com/fwlink/?LinkID=397860

namespace HelpMEServices.Controllers
{
    [Route("api/[controller]")]
    public class UsersWhoAttendingEventController : Controller
    {
        private readonly AppDbContext _context;

        public UsersWhoAttendingEventController(AppDbContext context)
        {
            this._context = context;
        }


        // GET: api/values
        [HttpGet]
        public IEnumerable<UserWhoAttending> Get()
        {
            return _context.UsersWhoAttending.ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public UserWhoAttending Get(int id)
        {
            var userWhoAttending = _context.UsersWhoAttending.FirstOrDefault(u => u.Id == id);
            if (userWhoAttending != null)
            {
                return userWhoAttending;
            }
            else
            {
                var usr = new UserWhoAttending { };
                return usr;
            }

        }

        // POST api/values
        [HttpPost]
        public IActionResult Post([FromBody]UserWhoAttending user)
        {
            if (user != null)
            {
                //var last=_context.UsersWhoAttending.Last();
                //var id = last.Id + 1;
                //user.Id = id;
                _context.UsersWhoAttending.Add(user);
                _context.SaveChanges();
                return new ObjectResult(user);
            }
            else
            {
                var usr = new UserWhoAttending { };
                return new ObjectResult(usr);
            }
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var existingUser = _context.UsersWhoAttending.FirstOrDefault(u => u.UserId == id);
            _context.UsersWhoAttending.Remove(existingUser);
            _context.SaveChanges();

            var usr = new UserWhoAttending { };
            return new ObjectResult(usr);
        }
    }
}
