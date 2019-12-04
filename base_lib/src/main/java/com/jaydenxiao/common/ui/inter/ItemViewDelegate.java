package com.jaydenxiao.common.ui.inter;

/*
    示例: 每种Item类型对应一个ItemViewDelegete，例如：
    public class MsgComingItemDelagate implements ItemViewDelegate<ChatMessage> {

        @Override
        public int getItemViewLayoutId()
        {
            return R.layout.main_chat_from_msg;
        }

        @Override
        public boolean isForViewType(ChatMessage item, int position)
        {
            return item.isComMeg();
        }

        @Override
        public void convert(ViewHolder holder, ChatMessage chatMessage, int position)
        {
            holder.setText(R.id.chat_from_content, chatMessage.getContent());
            holder.setText(R.id.chat_from_name, chatMessage.getName());
            holder.setImageResource(R.id.chat_from_icon, chatMessage.getIcon());
        }
    }
*/

import com.jaydenxiao.common.ui.holder.ViewHolder;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
