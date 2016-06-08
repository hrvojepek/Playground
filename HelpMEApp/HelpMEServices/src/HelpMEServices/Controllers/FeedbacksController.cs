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
    public class FeedbacksController : Controller
    {
        private readonly AppDbContext _context;

        public FeedbacksController(AppDbContext context)
        {
            this._context = context;
        }
        // GET: api/feedbacks
        [HttpGet]
        public IEnumerable<Feedback> Get()
        {
            return _context.Feedbacks;
        }

        // POST api/feedbacks
        [HttpPost]
        public IActionResult Post([FromBody]Feedback feedback)
        {
            if (feedback != null)
            {
                if (feedback.Id == 0)
                {
                    _context.Feedbacks.Add(feedback);
                    _context.SaveChanges();
                    return new ObjectResult(feedback);
                }
                else
                {
                    var existingFeedback = _context.Feedbacks.FirstOrDefault(f => f.Id == feedback.Id);
                    existingFeedback.UserName = feedback.UserName;
                    existingFeedback.UserPhoto = feedback.UserPhoto;
                    existingFeedback.FeedbackComment = feedback.FeedbackComment;
                    _context.SaveChanges();
                    return new ObjectResult(existingFeedback);
                }
            }

            else
            {
                return new ObjectResult(null);

            }
            
        }

        // DELETE api/feedbacks/5
        [HttpDelete("{id}")]
        public IActionResult Delete(int id)
        {
            var feedback = _context.Feedbacks.FirstOrDefault(f => f.Id == id);
            _context.Feedbacks.Remove(feedback);
            _context.SaveChanges();
            return new HttpStatusCodeResult(200);
        }
    }
}
