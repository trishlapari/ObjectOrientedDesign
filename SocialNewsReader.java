/*
# ObjectOrientedDesign
# AppendingTags in text

We are building a social newsreader. There will be 3 modules:
1) The first module will crawl the Facebook and Twitter feeds.
2) The second module will extract important concepts from the feed. i.e. entities, links or twitter
usernames. (In the example below, you can see how there is an entity from position 14 through
22, another entity from position 0 to 5 and so on).
3) The third module will format each post as follows:
● Entities should be wrapped in "strong" tags
● Links should be wrapped in "a href" tags that point to the corresponding links
● Twitter usernames should be wrapped in "a href" tags that point to
"http://twitter.com/username" and are displayed as the username

Example
Module One Output:
“Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile”
Module Two Output (in an appropriate object of your choosing):
positions 14 through 22 → Entity
positions 0 through 5 → Entity
positions 55 through 67 → Twitter username
positions 37 through 54 → Link
Module Three Output (takes as input the outputs of modules 1 and 2):
<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a
href=”http://bit.ly/xyz”>http://bit.ly/xyz </a> @ <a
href=”http://twitter.com/elversatile”>elversatile</a>
*/

import java.lang.*; 
import java.util.*;

public class SocialNewsReader  {
    
    enum TagType 
    { 
    Entity, TwitterUsername, Link; 
    }
    
    public static class EntityPosition implements Comparable<EntityPosition>{
        
    private final Integer from, to;    
    private final TagType type;  
    
    public EntityPosition(Integer from_value, Integer to_value, TagType type_value) {
        from = from_value;  
        to = to_value;
        type = type_value;
    }
     
    public Integer getFrom(){
        return from;
    }
    public Integer getTo(){
        return to;
    }
    public TagType getType(){
        return type;
    }
    
    public int compareTo(EntityPosition compareEntity) {
        int comparefrom = ((EntityPosition) compareEntity).from; 
        return this.from - comparefrom;
    }
	
    public static Comparator<EntityPosition> EntityPositionComparator = new Comparator<EntityPosition>() {
        public int compare(EntityPosition entity1, EntityPosition entity2) {
            return (entity1.from).compareTo(entity2.from);
        }
    };
  };
    
    public class Tag { 
		protected String tag_prefix1, tag_prefix2, tag_suffix;
		public String appendTag(EntityPosition post, String text, int shift){
            return "";
		}

		public Integer getShift(String word){
            return 0;
		}
    } 
    
    public class Entity extends Tag{
		public Entity(String tag_prefix_value1, String tag_suffix_value) {
            tag_prefix1 = tag_prefix_value1;
            tag_suffix = tag_suffix_value;
		} 

		public String appendTag(EntityPosition post, String text, int shift){
			Integer start =  shift + post.getFrom();
			Integer end =  shift + post.getTo();
			String tag_prefix = tag_prefix1;
			String formattedPost= text.substring( 0, start ) + tag_prefix + text.substring( start, end ) + tag_suffix + text.substring( end );
			return formattedPost;
		}

		public Integer getShift(String word){
			return tag_prefix1.length() + tag_suffix.length();
		}
    };
    
    public class Twitter_username extends Tag{
		public Twitter_username(String tag_prefix_value1, String tag_prefix_value2, String tag_suffix_value) {
			tag_prefix1 = tag_prefix_value1;
			tag_prefix2 = tag_prefix_value2;
			tag_suffix = tag_suffix_value;
		} 

		public String appendTag(EntityPosition post, String text, int shift){
			Integer start =  shift + post.getFrom() + 1;
			Integer end =  shift + post.getTo();
			String tag_prefix = tag_prefix1 + text.substring( start, end ) + tag_prefix2;
			String formattedPost= text.substring( 0, start ) + tag_prefix + text.substring( start, end ) + tag_suffix + text.substring( end );
			return formattedPost;
			}

		public Integer getShift(String word){
			return tag_prefix1.length() + tag_prefix2.length() + tag_suffix.length() + word.length()-1;
			}
    };
    
    public class Link extends Tag{
		public Link(String tag_prefix_value1, String tag_prefix_value2, String tag_suffix_value) {
			tag_prefix1 = tag_prefix_value1;
			tag_prefix2 = tag_prefix_value2;
			tag_suffix = tag_suffix_value;
		} 

		public String appendTag(EntityPosition post, String text, int shift){
			Integer start =  shift + post.getFrom();
			Integer end =  shift + post.getTo();
			String tag_prefix = tag_prefix1 + text.substring( start, end ) + tag_prefix2;
			String formattedPost= text.substring( 0, start ) + tag_prefix + text.substring( start, end ) + tag_suffix + text.substring( end );
			return formattedPost;
		}

		public Integer getShift(String word){
			return tag_prefix1.length() + tag_prefix2.length() + tag_suffix.length() + word.length();
			}
    };
    
    Entity entity = new Entity("<strong>", "</strong>" );
    Twitter_username twitter_username = new Twitter_username("<ahref=\"http://twitter.com/", "\">", "</a>");
    Link link = new Link("<ahref=\"", "\">", "</a>");
    
    String formatPost( EntityPosition[] post, String feed){
        String formattedPost = feed;
        Integer shift = 0, start_index =0 , end_index = 0;
        Arrays.sort(post, EntityPosition.EntityPositionComparator);
        Tag tag;
        for(int i=0;i<post.length; i++){
            start_index = shift + post[i].getFrom();
            end_index = shift + post[i].getTo();
            
            if(post[i].getType() == TagType.Entity){ 
                tag = entity;
            }
            else if(post[i].getType() == TagType.TwitterUsername){ 
                tag = twitter_username;
                start_index += 1;
            }
            else if(post[i].getType() == TagType.Link){
                tag = link;
            }
            else{
                System.out.println("Invalid tag. Skipping");
                continue;
            }
            String word = formattedPost.substring( start_index, end_index );
            formattedPost = tag.appendTag(post[i], formattedPost, shift);
            shift += tag.getShift(word);
        }
        return formattedPost;
    }
    
    public static void main(String[] args) {
        String feed= "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";
        SocialNewsReader s1 = new SocialNewsReader(); 
        EntityPosition[] a1 = new EntityPosition[4];
        EntityPosition p1 = new EntityPosition(0,5,TagType.Entity);
        EntityPosition p2 = new EntityPosition(14,22,TagType.Entity); 
        EntityPosition p3 = new EntityPosition(55,67,TagType.TwitterUsername); 
        EntityPosition p4 = new EntityPosition(37,54,TagType.Link);
        a1[0] = p1; a1[1] = p2; a1[2] = p3; a1[3] = p4;
        String output = s1.formatPost(a1, feed);
        System.out.println(output);
    }

}
