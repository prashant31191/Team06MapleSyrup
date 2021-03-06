/*
 * Copyright 2014 Anni Dai
 * Copyright 2014 Bicheng Yan
 * Copyright 2014 Liwen Chen
 * Copyright 2014 Liang Jingjing
 * Copyright 2014 Xiaocong Zhou
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualberta.app.comparator;

import java.util.Comparator;

import ca.ualberta.app.models.Answer;

/**
 * A comparator class which compute 2 Comment object's score and sort them by
 * Question Upvote.
 */
public class AnswerListUpvoteComparator implements Comparator<Answer> {
	/**
	 * @param a
	 *            : left hand side Question
	 * @param b
	 *            : right hand side Question
	 * @return -1 if left hand side Question's Upvote is higher than the right
	 *         hand side Question's Upvote 1 otherwise.
	 */
	@Override
	public int compare(Answer a, Answer b) {
		if (a.getAnswerUpvoteCount() > b.getAnswerUpvoteCount()) {
			return -1;
		} else if (a.getAnswerUpvoteCount() < b.getAnswerUpvoteCount()) {
			return 1;
		} else if (a.getTimestamp().getTime() >= b.getTimestamp().getTime()) {
			return -1;
		} else {
			return 1;
		}
	}

}