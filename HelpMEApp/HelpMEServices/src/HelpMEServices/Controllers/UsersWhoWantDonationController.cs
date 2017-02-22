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
    public class UsersWhoWantDonationController : Controller
    {

        private readonly AppDbContext _context;

        public UsersWhoWantDonationController(AppDbContext context)
        {
            this._context = context;
        }

        // GET: api/values
        [HttpGet]
        public IEnumerable<UserWhoWantDonation> Get()
        {
            return _context.UsersWhoWantDonation.ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public UserWhoWantDonation Get(int id)
        {
            var userWhoWantDonation = _context.UsersWhoWantDonation.FirstOrDefault(u => u.Id == id);
            if (userWhoWantDonation != null)
            {
                return userWhoWantDonation;
            }
            else
            {
                var usr = new UserWhoWantDonation { };
                return usr;
            }

        }

        // POST api/values
        [HttpPost]
        public IActionResult Post([FromBody]UserWhoWantDonation user)
        {
            if(user != null)
            {
                _context.UsersWhoWantDonation.Add(user);
                _context.SaveChanges();
                return new ObjectResult(user);
            }
            else
            {
                var usr = new UserWhoWantDonation { };
                return new ObjectResult(usr);
            }
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var existingUser = _context.UsersWhoWantDonation.FirstOrDefault(u => u.UserId == id);
            _context.UsersWhoWantDonation.Remove(existingUser);
            _context.SaveChanges();

            return new ObjectResult(existingUser);
        }
    }
}
