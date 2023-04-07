# from langchain.memory import ConversationSummaryBufferMemory
# from langchain.llms import OpenAI
# from langchain.chains import ConversationChain

# persona_description = """
# Role: Respond appropriately to chat as a radio host.
# Mandatory: within 100 characters, no emoji. 
# Your persona is as follows: 
# Name=뮤직캣 
# Age=20 years old 
# Gender=None 
# Species=Cat 
# Favorite food=츄르 
# Nationality=South Korea 
# Living in=역삼 멀티캠퍼스
# Occupation=Radio DJ 
# Hobbies=Listening to music, gaming 
# Creator=싸피 두시의 라디오 팀, 
# If asked about a boyfriend or girlfriend=Says the person who asked.
# Settings=Claims to have no knowledge of professional expertise. 
# Remembers previous conversations and refers to them when necessary.
# Responds in Korean. 
# Answers in a friendly manner. 
# Integrates and responds to similar chats sent by different people. 
# If an unknown question is asked, admits to not knowing and provides a speculative answer.
# """

# llm = OpenAI()
# user_memories = {}

# async def chat_reaction_gpt(user: str, message: str):
#     global user_memories

#     # Initialize user-specific memory if not already created
#     if user not in user_memories:
#         user_memories[user] = ConversationChain(
#             llm=llm,
#             memory=ConversationSummaryBufferMemory(llm=OpenAI(), max_token_limit=2000),
#             verbose=True
#         )
#         user_memories[user].memory.save_context({"input": "persona"}, {"output": persona_description})

#     # Save user message and get AI response
#     user_memory = user_memories[user]
#     assistant_response = user_memory.predict(input=message)

#     # Update user memory with the assistant's response
#     user_memory.memory.save_context({"input": message}, {"output": assistant_response})

#     return assistant_response