# Evolved Time - Task Management App
A task management app that sorts your goals of the day based on a set of criteria. I believe this can help people be more productive.

## **3 factors to a task**:

1. Difficultly: How hard is the task
2. Time Commitment: How long will this task take
3. Personal Interest: How much does the user enjoy doing this activity

## Difficulty: How hard is the actual task

Difficulty is interesting since its quite subjective, so if 2 people had the same tasks, but felt it was different
difficulty, it would move the task by possibly quite a lot. The thing to keep in mind is **usually the harder the task,
the longer it will take**. Now this is not always the case. Running a 100-meter sprint is hard, but **very quick**, so
this doesn't always work. However, another thing to keep in mind is if something is considered difficult, they most
likely are going to be talking about a big project for school, studying for a test, preparing for a presentation, etc...
So **the higher the difficulty, the higher in priority**. It is important the user has time to get this task done in
case they need extra time for research. No one wants to do difficult tasks later in the day, when your energy is at your
lowest. It will also go hand in hand with **Time Commitment**

## Time Commitment: How long will this task

Now unlike difficulty that may or may not take a long time, **time commitment directly correlates to how long the task
will take**. So now if we go back to the 100-meter sprint, it may be a 9 in difficulty, but 4 or 5 in time commitment.
This would make something like maybe studying for a test less difficult, but take longer, possibly putting it higher in
the list. Of course this will be subject for everyone, so it is key users are **as accurate as possible when rating
their tasks**. The algorithm cannot do its job properly if the user just puts random numbers. Nonetheless, I think it is
obvious here **the higher the time commitment, the higher priority**. You only have so many hours in the day, so you
need to get the longer tasks done early.

## Personal Interest: How much does the user enjoy doing this activity

This is my personal favorite category, as it relates to **how much does the user enjoy this activity?** Now this may not
sound great at first, but **the higher the personal interest, the lower priority**. Now there is a reason you save
desert for last. It is the most delicious part of the meal, and really ends your day in the bang. Now sure, you had to
wait, maybe eat a salad you weren't a fan of, and all you could think of was that melted chocolate chip cookie, but *
*the very anticipation of the activity, makes it that more enjoyable**. Image you had a cookie before dinner, now sure
maybe dinner is great and delicious, but nothing will compare to that desert, and so it will feel off. So for now, if a
user puts their interest very high, it will lower priority, so that way they can enjoy their fun activities at the end
of their night. Now of course there is a caveat to this. If a user puts **too many tasks**, they may **never get to the
enjoyable ones**, thus it is up to the users due diligence to not overload themselves, so they can have a well-rounded
day. This will come with some trial and error, but they will begin to understand how fun ending the day with desert
really is.

## The Algorithm

Each assignment will receive a "score". The higher the number, the higher on the list. If 2 tasks have the same number,
the older task will be preferred:

### (d * 1.5t) / p

Difficulty 'd' is multiplied by time commitment 't', which is already multipled by 1.5, and all of that is divded by the personal interest 'p'.

The goal with this formula is to have the most boring/busiest tasks completed first, and the most enjoyable tasks
completed at the end of the day. This is what has worked best for me and I hope it can help other people be more
productive.

## Tools Learned
- JavaFX
- Scene Builder
- Maven
- IntelliJ
## Design vs. Final Product
![1](Images\design.png)
![1](Images\finalproduct1.png)
![1](Images\finalproduct2.png)
![1](Images\finalproduct3.png)
## Opinion on Design vs. Final Product
I think I actually did a pretty decent job at the final product in regards to the design. For starters, It does its job at adding tasks, and sorting them based on a set of criteria. Along with that, I made sure to add an extra menu giving more information to the user. I also managed to add a menu bar to give a cleaner look and give the user more options.

In future iterations, I hope to implement a database for more reliable persistence, add a calendar, and a much more colorful UI.  

## Overall Lessons learned
I found this a fun project, I designed something, and then made it. To be honest this is actually really cool, I came up with something from my brain, and then created it in code. Sure it took a lot of research, youtube, hitting my head, and a *tad* of chatgpt, but overall I am proud for what I did. 

This project took about **4 days straight, with about 6 hours a day**, totaling to around 24 hours of total work, more or less. I did not realize how quick I got this project done. I thought this would take weeks, I was mentally prepared to take longer, but I actually got it done quicker than I thought. 

Now, with all that being said, if I am going to be honest, I think I could have slowed down a bit. I tend to say my biggest weakness is patience, and this was no different. Don't get me wrong, this project was mostly me combining resources from everywhere, but I feel I could have slowed down, and maybe come up with some more ideas of my own. 

I am proud for coming up with a design, and following up on it, but I think I am capable of more. If I spend more time designing, and build this project at a slower pace, I think I can increase the quality of the project, while also possibly making something more satisfying. I will try to use **zero AI tools** to assist. While I didn't use a lot, I definitely used more than I would have liked.

Nonetheless, I am proud of the project I made, and am looking forward for the many more projects to come.
