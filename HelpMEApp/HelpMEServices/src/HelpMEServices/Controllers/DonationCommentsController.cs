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
    public class DonationCommentsController : Controller
    {

        private readonly AppDbContext _context;

        public DonationCommentsController(AppDbContext context)
        {
            this._context = context;
        }

        // GET: api/values
        [HttpGet]
        public IEnumerable<CommentDonation> Get()
        {
            return _context.DonationComments.ToList();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var comm = _context.DonationComments
                .FirstOrDefault(d => d.Id == id);

            if (comm != null)
            {
                return new ObjectResult(comm);
            }
            else
            {
                var c = new CommentDonation
                {
                };

                return new ObjectResult(c);
            }
        }

        // POST api/eventcomments
        public IActionResult Post([FromBody]CommentDonation comment)
        {
            if (comment != null)
            {
                if (comment.Id == 0)
                {
                    comment.TimeCreated = DateTime.Now.AddHours(-2);
                    _context.DonationComments.Add(comment);
                    _context.SaveChanges();
                    return new ObjectResult(comment);
                }
                else
                {
                    var existingComment = _context.DonationComments.FirstOrDefault(f => f.Id == comment.Id);
                    existingComment.UserName = comment.UserName;
                    existingComment.UserPhoto = comment.UserPhoto;
                    existingComment.CommentTxt = comment.CommentTxt;
                    existingComment.DonationId = comment.DonationId;
                    existingComment.TimeCreated = comment.TimeCreated;
                    existingComment.UserSurname = comment.UserSurname;
                    _context.SaveChanges();
                    return new ObjectResult(existingComment);
                }
            }

            else
            {
                return new ObjectResult(null);

            }

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
