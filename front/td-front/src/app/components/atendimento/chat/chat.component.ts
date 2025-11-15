import { Component, OnInit } from '@angular/core';

interface Message {
  id: number;
  text: string;
  sender: 'user' | 'support';
  timestamp: Date;
  status?: 'sent' | 'delivered' | 'read';
}

interface Conversation {
  id: number;
  name: string;
  avatar: string;
  lastMessage: string;
  lastMessageTime: Date;
  unread: number;
  isOnline?: boolean;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  selectedConversation: Conversation | null = null;
  messages: Message[] = [];
  newMessage: string = '';
  searchTerm: string = '';

  conversations: Conversation[] = [
    {
      id: 1,
      name: 'Suporte Técnico',
      avatar: '🎧',
      lastMessage: 'Como posso ajudá-lo hoje?',
      lastMessageTime: new Date(Date.now() - 300000),
      unread: 0,
      isOnline: true
    },
    {
      id: 2,
      name: 'Cliente - João Silva',
      avatar: '👤',
      lastMessage: 'Preciso de ajuda com a OS #1234',
      lastMessageTime: new Date(Date.now() - 600000),
      unread: 2
    },
    {
      id: 3,
      name: 'Cliente - Maria Santos',
      avatar: '👩',
      lastMessage: 'Quando será entregue?',
      lastMessageTime: new Date(Date.now() - 3600000),
      unread: 1
    },
    {
      id: 4,
      name: 'Equipe Técnica',
      avatar: '👨‍🔧',
      lastMessage: 'Status da OS atualizado',
      lastMessageTime: new Date(Date.now() - 7200000),
      unread: 0,
      isOnline: true
    }
  ];

  constructor() { }

  ngOnInit(): void {
    // Seleciona a primeira conversa por padrão
    if (this.conversations.length > 0) {
      this.selectConversation(this.conversations[0]);
    }
  }

  selectConversation(conversation: Conversation): void {
    this.selectedConversation = conversation;
    conversation.unread = 0;
    this.loadMessages(conversation.id);
  }

  loadMessages(conversationId: number): void {
    // Mensagens mockadas
    this.messages = [
      {
        id: 1,
        text: 'Olá! Como posso ajudá-lo hoje?',
        sender: 'support',
        timestamp: new Date(Date.now() - 3600000),
        status: 'read'
      },
      {
        id: 2,
        text: 'Preciso de ajuda com minha ordem de serviço',
        sender: 'user',
        timestamp: new Date(Date.now() - 3500000),
        status: 'delivered'
      },
      {
        id: 3,
        text: 'Claro! Qual é o número da OS?',
        sender: 'support',
        timestamp: new Date(Date.now() - 3400000),
        status: 'read'
      },
      {
        id: 4,
        text: 'É a OS #1234',
        sender: 'user',
        timestamp: new Date(Date.now() - 3300000),
        status: 'delivered'
      },
      {
        id: 5,
        text: 'Verificando o status da OS #1234...\nEla está em "Em Andamento" e será concluída até amanhã.',
        sender: 'support',
        timestamp: new Date(Date.now() - 3200000),
        status: 'read'
      }
    ];
  }

  sendMessage(): void {
    if (!this.newMessage.trim() || !this.selectedConversation) return;

    const message: Message = {
      id: this.messages.length + 1,
      text: this.newMessage,
      sender: 'user',
      timestamp: new Date(),
      status: 'sent'
    };

    this.messages.push(message);
    this.selectedConversation.lastMessage = this.newMessage;
    this.selectedConversation.lastMessageTime = new Date();
    this.newMessage = '';

    // Simular resposta automática após 2 segundos
    setTimeout(() => {
      const response: Message = {
        id: this.messages.length + 1,
        text: 'Mensagem recebida! Estamos analisando sua solicitação.',
        sender: 'support',
        timestamp: new Date(),
        status: 'read'
      };
      this.messages.push(response);
      this.selectedConversation!.lastMessage = response.text;
      this.selectedConversation!.lastMessageTime = new Date();
    }, 2000);
  }

  formatTime(date: Date): string {
    const now = new Date();
    const diff = now.getTime() - date.getTime();
    const minutes = Math.floor(diff / 60000);
    
    if (minutes < 1) return 'agora';
    if (minutes < 60) return `${minutes}min`;
    
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours}h`;
    
    return date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit' });
  }

  formatMessageTime(date: Date): string {
    return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
  }

  get filteredConversations(): Conversation[] {
    if (!this.searchTerm) return this.conversations;
    return this.conversations.filter(c => 
      c.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
