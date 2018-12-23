package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String addComment(@PathVariable("imageId") Integer imageId, @RequestParam(name = "comment") String text, HttpSession session, Model model) {
        Comment comment = new Comment();
        Image image = imageService.getImage(imageId);
        comment.setImage(image);
        User user = (User) session.getAttribute("loggeduser");
        comment.setUser(user);
        comment.setText(text);
        comment.setCreatedDate(new Date());
        commentService.addComment(comment);

        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        List<Comment> comments = commentService.getAllComments(image);
        model.addAttribute("comments", comments);
        return "images/image";
    }
}
